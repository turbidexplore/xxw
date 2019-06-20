package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Message;
import com.bangxuan.xxw.service.NewsClassService;
import com.bangxuan.xxw.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/news")
@CrossOrigin
public class NewsController {

    @Autowired
    private NewsClassService newsClassService;

    @Autowired
    private NewsService newsService;

    @GetMapping("/getNews")
    public Mono<Message> getNews(){
        JSONArray data = new JSONArray();
        newsClassService.all().forEach(nc->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("class",nc);
            jsonObject.put("data",newsService.allByPID(nc.getString("id")));
            data.add(jsonObject);
        });
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/getById")
    public Mono<Message> getById(@RequestParam("id")String id){
        return Mono.just(Message.SCUESSS(Message.SECUESS,newsService.getById(id)));
    }
}
