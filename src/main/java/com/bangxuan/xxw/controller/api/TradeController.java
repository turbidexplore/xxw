package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Message;
import com.bangxuan.xxw.entity.ProductClass;
import com.bangxuan.xxw.service.ProductClassService;
import com.bangxuan.xxw.service.TradeService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;


@Api(value = "By Trade",description = "行业类型")
@RestController
@RequestMapping("/trade")
@CrossOrigin
public class TradeController {

    @Autowired
    private TradeService tradeService;


    @Autowired
    private ProductClassService productClassService;

    @ApiOperation("获取层级行业类型")
    @GetMapping("/getbylevel")
    public Mono<Message> getByLevel(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,get(tradeService.getByPcode(),"0")));
    }

    public JSONArray get(List<JSONObject> list,String code){
        JSONArray jsonArray=new JSONArray();
        list.forEach(t->{
           if(t.getString("pcode").equals(code)){
               t.put("data",get(list,t.getString("id")));
               jsonArray.add(t);
           }
        });
       return jsonArray;
    }

    @ApiOperation("根据行业三级查询产品四五级")
    @GetMapping("/getClassByPcode")
    public Mono<Message> getClassByPcode(@RequestParam("pcode")String pcode){
        return Mono.just(Message.SCUESSS(Message.SECUESS, tradeService.getByClassPcode(pcode)));
    }






}
