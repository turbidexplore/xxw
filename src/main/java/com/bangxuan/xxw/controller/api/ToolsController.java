package com.bangxuan.xxw.controller.api;


import com.bangxuan.xxw.util.CodeLib;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Api(value = "By ProductClass",description = "工具")
@RestController
@RequestMapping("/tools")
@CrossOrigin
public class ToolsController {

    @ApiOperation("md5加密")
    @PostMapping("/md5")
    public Mono<String> md5(@RequestParam("str") String str){
        return Mono.just(CodeLib.MD5(str));
    }
}
