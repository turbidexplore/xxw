package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Message;
import com.bangxuan.xxw.service.NewsClassService;
import com.bangxuan.xxw.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Api(value = "By News",description = "新闻")
@RestController
@RequestMapping("/news")
@CrossOrigin
public class NewsController {

    @Autowired
    private NewsClassService newsClassService;

    @Autowired
    private NewsService newsService;

    @GetMapping("/getNews")
    @ApiOperation("获取新闻信息")
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
    @ApiOperation("通过id获取新闻信息")
    public Mono<Message> getById(@RequestParam("id")String id){
        return Mono.just(Message.SCUESSS(Message.SECUESS,newsService.getById(id)));
    }
}
