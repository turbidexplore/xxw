package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Brand;
import com.bangxuan.xxw.util.Message;
import com.bangxuan.xxw.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/name")
    public Mono<Message> name(@RequestParam("id")String id){
        return Mono.just(Message.SCUESSS(Message.SECUESS,brandService.name(id)));
    }

    @GetMapping("/all")
    public Mono<Message> all(@RequestParam("ids")String ids){
        JSONObject data = new JSONObject();
        data.put("brand_names",brandService.all(ids.split(",")));
        data.put("brand_areas",brandService.areas(ids.split(",")));
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/getByCompanyId")
    public Mono<Message> getByCompanyId(@RequestParam("id")String id){
        return Mono.just(Message.SCUESSS(Message.SECUESS,brandService.getByCompanyId(id)));
    }

    @PutMapping("/update")
    public Mono<Message> update(@RequestBody Brand brand){
        return Mono.just(Message.SCUESSS(Message.SECUESS,brandService.update(brand)));
    }

    @GetMapping("/list")
    public Mono<Message> list(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,brandService.list()));
    }


}
