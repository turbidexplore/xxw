package com.bangxuan.xxw.controller.api;

import com.bangxuan.xxw.entity.Message;
import com.bangxuan.xxw.service.OcrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ocr")
@Api(value = "By OCR",description = "计算机视觉识别")
@CrossOrigin
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @GetMapping("/word")
    @ApiOperation("通过图片url获取识别信息")
    public Mono<Message> word(@RequestParam("url")String url){

        return Mono.just(Message.SCUESSS("ok",ocrService.word(url)));
    }

    @GetMapping("/table")
    @ApiOperation("通过图片url获取识别信息")
    public Mono<Message> table(@RequestParam("url")String url) throws Exception {

        return Mono.just(Message.SCUESSS("ok", ocrService.table(url)));
    }
//
//    @PostMapping("/tabledata")
//    @ApiOperation("通过id获取识别信息")
//    public Mono<Message> tabledata(@RequestParam("id")String id)  {
//
//        return Mono.just(Message.SCUESSS("ok",ocrService.tabledata(id)));
//    }
}
