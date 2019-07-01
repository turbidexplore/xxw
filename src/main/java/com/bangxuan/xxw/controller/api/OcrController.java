package com.bangxuan.xxw.controller.api;

import com.bangxuan.xxw.util.Message;
import com.bangxuan.xxw.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ocr")
@CrossOrigin
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @GetMapping("/word")
    public Mono<Message> word(@RequestParam("url")String url){
        return Mono.just(Message.SCUESSS("ok",ocrService.word(url)));
    }

    @GetMapping("/table")
    public Mono<Message> table(@RequestParam("url")String url) throws Exception {
        return Mono.just(Message.SCUESSS("ok", ocrService.table(url)));
    }

}
