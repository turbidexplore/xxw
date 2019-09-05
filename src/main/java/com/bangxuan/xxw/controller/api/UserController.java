package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.service.AreaService;
import com.bangxuan.xxw.util.Message;
import com.bangxuan.xxw.entity.User;
import com.bangxuan.xxw.service.UserSecurityService;
import com.bangxuan.xxw.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
@Api(description = "UserController接口")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private AreaService areaService;

    @PutMapping("/update")
    public Mono<Message> update(Principal principal,@RequestBody User user){
        user.setCode(userSecurityService.findUserCodeByPhone(principal.getName()));
        return Mono.just(Message.SCUESSS(Message.SECUESS,userService.update(user)));
    }
    @ApiOperation(value = "登陆" ,  notes="登陆")
    @GetMapping("/get")
    public Mono<Message> get(Principal principal){
        try {
            if(principal!=null){
                User user= userService.get(principal.getName());
                // 国家
                JSONObject jsonCountry = areaService.getById(user.getCompany_country());
                if(jsonCountry!=null){
                    user.setCompany_country_name(jsonCountry.getString("area_name"));
                }

                //省
                JSONObject jsonProvince = areaService.getById(user.getCompany_province());
                if(jsonProvince!=null){
                    user.setCompany_province_name(jsonProvince.getString("area_name"));
                }

                //城市
                JSONObject jsonCity = areaService.getById(user.getCompany_city());
                if(jsonCity!=null){
                    user.setCompany_city_name(jsonCity.getString("area_name"));
                }

                //区域
                JSONObject jsonArea = areaService.getById(user.getCompany_area());
                if(jsonArea!=null){
                    user.setCompany_area_name(jsonArea.getString("area_name"));
                }

                user.setMobile(principal.getName());
                return Mono.just(Message.SCUESSS(Message.SECUESS,user));
            }else{
                return Mono.just(Message.SCUESSS(Message.SECUESS,new Object()));
            }
        }catch (Exception e){
            return Mono.just(Message.ERROR(Message.ERROR));
        }
    }

    @GetMapping("/getAllByPage")
    public Mono<Message> get(int page,int size,String text){
        return Mono.just(Message.SCUESSS(Message.SECUESS,userService.getAllByPage(page,size,text)));
    }

    @GetMapping("/getUserCount")
    public Mono<Message> getUserCount(){
        JSONArray data=new JSONArray();
        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
        data.add(userService.getUserCountByTime("-"));
        data.add(userService.getUserCountByTime(strNow[0]));
        data.add(userService.getUserCountByTime(strNow[0]+"-"+strNow[1]));
        data.add(userService.getUserCountByTime(strNow[0]+"-"+strNow[1]+"-"+strNow[2]));
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

}
