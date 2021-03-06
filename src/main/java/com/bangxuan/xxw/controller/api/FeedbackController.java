package com.bangxuan.xxw.controller.api;

import com.bangxuan.xxw.dao.UserMapper;
import com.bangxuan.xxw.entity.Feedback;
import com.bangxuan.xxw.util.Message;
import com.bangxuan.xxw.service.FeedbackService;
import com.bangxuan.xxw.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

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
    public Mono<Message> getNews(@RequestBody Feedback feedback){
        feedbackService.add(feedback);
        userSecurityService.findByAdmin().forEach(u->{
            userMapper.updateFKCount(userMapper.get(u).getFkcount()+1,u);
        });
        return Mono.just(Message.SCUESSS(Message.SECUESS,null));
    }

    @GetMapping("/all")
    public Mono<Message> all(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,feedbackService.getAll()));
    }

    @PutMapping("/complete")
    public Mono<Message> complete(Principal principal, @RequestParam("id")String id,@RequestParam("text")String text){
        userMapper.updateFKCount(userMapper.get(principal.getName()).getFkcount()-1,principal.getName());
        return Mono.just(Message.SCUESSS(Message.SECUESS,feedbackService.update(id,principal.getName(),text)));
    }


}
