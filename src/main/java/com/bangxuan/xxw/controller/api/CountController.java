package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.service.CountService;
import com.bangxuan.xxw.service.ProductClassService;
import com.bangxuan.xxw.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/count")
@CrossOrigin
public class CountController {

    @Autowired
    private CountService countService;

    @Autowired
    private ProductClassService productClassService;

    @GetMapping("/all")
    public Mono<Message> all(){
        return Mono.just(Message.SCUESSS("ok",countService.count()));
    }

    @GetMapping("/userfiveclass")
    public Mono<Message> userfiveclass(){
        return Mono.just(Message.SCUESSS("ok",countService.userfiveclass()));
    }

    @GetMapping("/day")
    public String day() throws Exception {
        return countService.day();
    }

    @PutMapping("/followClass")
    public Mono<Message> followClass(Principal principal, HttpServletRequest request, @RequestParam("id")String id,@RequestParam("ip")String ip) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("ip",ip);
        jsonObject.put("userinfo",principal.getName());
        jsonObject.put("classid",id);
        jsonObject.put("companyid",productClassService.getCompany(id).getId());
        return Mono.just(Message.SCUESSS("ok",countService.followClass(jsonObject)));
    }

    @GetMapping("/followInfo")
    public Mono<Message> followInfo(@RequestParam("page") int page) {
        return Mono.just(Message.SCUESSS(countService.followInfoCount().toString(),countService.followInfo()));
    }
}
