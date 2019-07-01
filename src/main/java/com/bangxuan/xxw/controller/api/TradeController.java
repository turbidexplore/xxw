package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.util.Message;
import com.bangxuan.xxw.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/trade")
@CrossOrigin
public class TradeController {

    @Autowired
    private TradeService tradeService;

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

    @GetMapping("/getClassByPcode")
    public Mono<Message> getClassByPcode(@RequestParam("pcode")String pcode){
        return Mono.just(Message.SCUESSS(Message.SECUESS, tradeService.getByClassPcode(pcode)));
    }






}
