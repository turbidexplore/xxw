package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Message;
import com.bangxuan.xxw.entity.User;
import com.bangxuan.xxw.entity.UserSecurity;
import com.bangxuan.xxw.entity.values.UserStatus;
import com.bangxuan.xxw.entity.values.UserType;
import com.bangxuan.xxw.service.UserSecurityService;
import com.bangxuan.xxw.service.UserService;
import com.bangxuan.xxw.util.CodeLib;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/usersecurity")
@CrossOrigin
public class UserSecurityController {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PutMapping("/register")
    public Mono<Message> register(@RequestBody UserSecurity userSecurity,@RequestParam("registertype")String registertype,HttpServletRequest httpServletRequest){
        UserSecurity info;
        try {
        switch (registertype){
            case "0":
                httpServletRequest.getParameter("navigator");
                String a="1X"+UUID.randomUUID().toString().substring(0,9);
                userSecurity.setRegister_ip(CodeLib.getLocalIp(httpServletRequest));
                userSecurity.setStatus(UserStatus.Normal);
                userSecurity.setPhonenumber(a);
                userSecurity.setType(UserType.test);
                userSecurity.setPassword(bCryptPasswordEncoder.encode("123456"));
                userSecurityService.add(userSecurity);
                MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
                map.add("grant_type","password");
                map.add("scope","web");
                map.add("login_type","password");
                map.add("username",a);
                map.add("password","123456");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth("website","turbid");
                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
                JSONObject object=restTemplate.postForObject("http://127.0.0.1:10001/oauth/token",request,JSONObject.class);
                return Mono.just(Message.SCUESSS("ok",object));
            case "1":
                info =userSecurityService.findBySMS(userSecurity.getPhonenumber(),userSecurity.getAuthcode());
                if(null!=info){
                    userSecurity.setRegister_ip(CodeLib.getLocalIp(httpServletRequest));
                    userSecurity.setUser_code(info.getUser_code());
                    userSecurity.setStatus(UserStatus.Normal);
                    userSecurity.setType(UserType.GeneralPersonal);
                    userSecurity.setPassword(bCryptPasswordEncoder.encode(userSecurity.getPassword()));
                    if(0==userService.findUserCountByCode(info.getUser_code())){
                        User user=new User();
                        user.setCode(info.getUser_code());
                        user.setNikename(new CodeLib().getNikeName(stringRedisTemplate));
                        user.setHeadportrait("https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/userinfo/201905220924522005932570390559257.png");
                        user.setCity(CodeLib.getAddressByIp(userSecurity.getRegister_ip()));
                        userSecurityService.updateByPhoneSelective(userSecurity);
                        userService.add(user);
                    }else {
                        return Mono.just(Message.SCUESSS("用户已存在",null));
                    }
                    return Mono.just(Message.SCUESSS("注册成功",null));
                }else {
                    return Mono.just(Message.ERROR("无效验证码"));
                }
            case "2":
                break;
            }
        }catch (Exception e){
            System.out.println(e);
            return Mono.just(Message.ERROR("系统异常"));
        }
        return Mono.just(Message.SCUESSS("注册成功",null));
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public Mono<Message> login(@RequestBody JSONObject jsonObject){
        try {
            MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
            map.add("grant_type","password");
            map.add("scope","web");
            map.add("login_type",jsonObject.getString("login_type"));
            map.add("username",jsonObject.getString("username"));
            map.add("password", jsonObject.getString("password"));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth("website","turbid");
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            JSONObject object=restTemplate.postForObject("http://127.0.0.1:10001/oauth/token",request,JSONObject.class);

            if (null==object){
                return Mono.just(Message.ERROR("登录失败"));
            }
            return Mono.just(Message.SCUESSS("登录成功",object));
        }catch (Exception e){
            System.out.println(e);
            return Mono.just(Message.ERROR("帐号或密码错误"));
        }
    }

    @PutMapping("/sms")
    public Mono<Message> sendSMS(@RequestParam(value = "phone") String phone){
        int count=userSecurityService.findCountByPhone(phone);
        int authcode=(int)((Math.random()*9+1)*100000);
        if(count==0){
            UserSecurity userSecurity=new UserSecurity();
            userSecurity.setPhonenumber(phone);
            userSecurity.setAuthcode(String.valueOf(authcode));
            userSecurity.setSendauthcode_time("sendtime");
            int add_status = userSecurityService.add(userSecurity);
        }else {
            int update_status= userSecurityService.updateAuthcode(phone,authcode);
        }
        int send_status = CodeLib.send(phone,authcode);
        if(send_status==1) {
            return Mono.just(Message.SCUESSS("短信发送成功", null));
        }else {
            return Mono.just(Message.ERROR("短信发送失败"));
        }
    }

    @GetMapping("/isRegister")
    public Mono<Message> isRegister( @RequestParam("phone")String phone){
        if(null==userService.get(phone)){
            return Mono.just(Message.SCUESSS("未注册", 0));
        }else {
            return Mono.just(Message.SCUESSS("已注册",1 ));
        }
    }

    @PutMapping("/changePassword")
    public Mono<Message> changePassword(Principal principal, @Param("password")String password){
        return Mono.just(Message.SCUESSS("成功", userSecurityService.changePassword(principal.getName(),password)));
    }

    @GetMapping("/userdata_count")
    public Mono<Message> userdata_count(){
        return Mono.just(Message.SCUESSS("SECUESS", userSecurityService.findUserdataCount()));
    }
}


