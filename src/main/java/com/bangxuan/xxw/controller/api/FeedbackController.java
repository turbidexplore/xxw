package com.bangxuan.xxw.controller.api;



import com.bangxuan.xxw.dao.UserMapper;
import com.bangxuan.xxw.entity.Feedback;
import com.bangxuan.xxw.entity.Message;
import com.bangxuan.xxw.service.FeedbackService;
import com.bangxuan.xxw.service.UserSecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Api(value = "By Feedback",description = "反馈")
@RestController
@RequestMapping("/feedback")
@CrossOrigin
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserSecurityService userSecurityService;

    @PutMapping("/add")
    @ApiOperation("反馈信息")
    public Mono<Message> getNews(@RequestBody Feedback feedback){
        feedbackService.add(feedback);
        userSecurityService.findByAdmin().forEach(u->{
            userMapper.updateFKCount(userMapper.get(u).getFkcount()+1,u);
        });
        return Mono.just(Message.SCUESSS(Message.SECUESS,null));
    }

    @GetMapping("/all")
    @ApiOperation("反馈信息")
    public Mono<Message> all(Principal principal){

        return Mono.just(Message.SCUESSS(Message.SECUESS,feedbackService.getAll()));
    }

    @PutMapping("/complete")
    @ApiOperation("完成反馈信息")
    public Mono<Message> complete(Principal principal, @RequestParam("id")String id,@RequestParam("text")String text){
        userMapper.updateFKCount(userMapper.get(principal.getName()).getFkcount()-1,principal.getName());
        return Mono.just(Message.SCUESSS(Message.SECUESS,feedbackService.update(id,principal.getName(),text)));
    }


}
