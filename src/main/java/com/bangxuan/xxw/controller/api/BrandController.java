package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Brand;
import com.bangxuan.xxw.entity.Message;
import com.bangxuan.xxw.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Api(value = "By brand",description = "品牌")
@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/name")
    @ApiOperation("名称")
    public Mono<Message> name(@RequestParam("id")String id){
        return Mono.just(Message.SCUESSS(Message.SECUESS,brandService.name(id)));
    }

    @GetMapping("/all")
    @ApiOperation("ALL")
    public Mono<Message> all(@RequestParam("ids")String ids){
        JSONObject data = new JSONObject();
        data.put("brand_names",brandService.all(ids.split(",")));
        data.put("brand_areas",brandService.areas(ids.split(",")));
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/getByCompanyId")
    @ApiOperation("通过企业id获取品牌")
    public Mono<Message> getByCompanyId(@RequestParam("id")String id){
        return Mono.just(Message.SCUESSS(Message.SECUESS,brandService.getByCompanyId(id)));
    }

    @PutMapping("/update")
    @ApiOperation("更新信息")
    public Mono<Message> update(@RequestBody Brand brand){
        return Mono.just(Message.SCUESSS(Message.SECUESS,brandService.update(brand)));
    }

    @GetMapping("/list")
    @ApiOperation("品牌墙")
    public Mono<Message> list(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,brandService.list()));
    }


}
