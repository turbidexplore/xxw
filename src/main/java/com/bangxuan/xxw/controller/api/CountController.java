package com.bangxuan.xxw.controller.api;

import com.bangxuan.xxw.util.Message;
import com.bangxuan.xxw.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/count")
public class CountController {

    @Autowired
    private CountService countService;

    @GetMapping("/all")
    public Mono<Message> all(){
        return Mono.just(Message.SCUESSS("ok",countService.count()));
    }
}
