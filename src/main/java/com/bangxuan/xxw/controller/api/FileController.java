package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.service.DaypdfCountService;
import com.bangxuan.xxw.service.FileService;
import com.bangxuan.xxw.service.ProductClassService;
import com.bangxuan.xxw.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

;

@RestController
@RequestMapping("/file")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductClassService productClassService;

    @Autowired
    private DaypdfCountService daypdfCountService;


    @PostMapping(value = "/upload")
    public Mono<Message> images(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", "https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/public/"+fileService.images(filePart,"public/")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/filelib")
    public Mono<Message> filelib(@RequestParam("file") MultipartFile[] fileParts) {
        try {
            List<String> files=new ArrayList<>();
            for (int i=0;i<fileParts.length;i++){
                files.add( "https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/filelib/"+fileService.images(fileParts[i],"filelib/"));
            }
            return Mono.just(Message.SCUESSS("上传成功",files));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/uploadUserhead")
    public Mono<Message> uploadUserhead(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/userinfo/"+ fileService.images(filePart,"userinfo/")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/uploadLogo")
    public Mono<Message> uploadLogo(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.images(filePart,"product_class/images/")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/uploadBrandLogo")
    public Mono<Message> uploadBrandLogo(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.images(filePart,"brand/logo/")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/uploadCompanyLogo")
    public Mono<Message> uploadCompanyLogo(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.images(filePart,"temp/")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/ocr")
    public Mono<Message> ocr(@RequestParam("file") MultipartFile filePart) {
        try {

            return Mono.just(Message.SCUESSS("上传成功", fileService.images(filePart,"ocr/")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/pdf")
    public Mono<Message> getPDF(Principal principal, @RequestParam("id") String id){
        JSONObject data=new JSONObject();
        data.put("basicurl","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/pdf/");
        data.put("data",productClassService.getPDF(id));
        data.put("company",productClassService.getCompany(id));
        data.put("brand",productClassService.getBrand(id));
        daypdfCountService.add(principal.getName());
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }


    @GetMapping("/ykpdf")
    public Mono<Message> ykpdf(@RequestParam("id") String id){
        JSONObject data=new JSONObject();
        data.put("company",productClassService.getCompany(id));
        data.put("brand",productClassService.getBrand(id));
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/workpdf")
    public Mono<Message> workpdf( @RequestParam("id") String id){
        JSONObject data=new JSONObject();
        data.put("basicurl","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/pdf/");
        data.put("data",productClassService.getPDF(id));
        data.put("company",productClassService.getCompany(id));
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

}
