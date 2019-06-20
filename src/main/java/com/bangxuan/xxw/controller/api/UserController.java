package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.bangxuan.xxw.entity.Message;
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


@Api(value = "By UserInfo",description = "用户信息")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @ApiOperation("添加用户信息")
    @PutMapping("/putinfo")
    public Mono<Message> addinfo(@RequestBody User user){
        return Mono.just(Message.SCUESSS(Message.SECUESS,null));
    }

    @ApiOperation("更新用户信息")
    @PutMapping("/update")
    public Mono<Message> update(Principal principal,@RequestBody User user){
        user.setCode(userSecurityService.findUserCodeByPhone(principal.getName()));
        return Mono.just(Message.SCUESSS(Message.SECUESS,userService.update(user)));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/get")
    public Mono<Message> get(Principal principal){

        try {
          User user= userService.get(principal.getName());
          user.setMobile(principal.getName());
            return Mono.just(Message.SCUESSS(Message.SECUESS,user));
        }catch (Exception e){
            return Mono.just(Message.ERROR(Message.ERROR));
        }
    }

    @ApiOperation("分页获取所有用户信息")
    @GetMapping("/getAllByPage")
    public Mono<Message> get(int page,int size,String text){
        return Mono.just(Message.SCUESSS(Message.SECUESS,userService.getAllByPage(page,size,text)));
    }

    @ApiOperation("获取用户数量")
    @GetMapping("/getUserCount")
    public Mono<Message> getUserCount(){
        JSONArray data=new JSONArray();
        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        data.add(userService.getUserCountByTime("-"));
        data.add(userService.getUserCountByTime(strNow[0]));
        data.add(userService.getUserCountByTime(strNow[0]+"-"+strNow[1]));
        data.add(userService.getUserCountByTime(strNow[0]+"-"+strNow[1]+"-"+strNow[2]));
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

}
