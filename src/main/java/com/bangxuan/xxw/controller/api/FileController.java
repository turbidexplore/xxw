package com.bangxuan.xxw.controller.api;


import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Message;;
import com.bangxuan.xxw.entity.values.UserType;
import com.bangxuan.xxw.service.DaypdfCountService;
import com.bangxuan.xxw.service.FileService;
import com.bangxuan.xxw.service.ProductClassService;
import com.bangxuan.xxw.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/file")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductClassService productClassService;

    @PostMapping(value = "/upload")
    public Mono<Message> images(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.images(filePart)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/filelib")
    public Mono<Message> filelib(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.filelib(filePart)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/uploadUserhead")
    public Mono<Message> uploadUserhead(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.imagesUserHead(filePart)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/uploadLogo")
    public Mono<Message> uploadLogo(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.logo(filePart)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/uploadBrandLogo")
    public Mono<Message> uploadBrandLogo(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.brandlogo(filePart)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/uploadCompanyLogo")
    public Mono<Message> uploadCompanyLogo(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.companyLogo(filePart)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/ocr")
    public Mono<Message> ocr(@RequestParam("file") MultipartFile filePart) {
        try {

            return Mono.just(Message.SCUESSS("上传成功", fileService.ocr(filePart)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    private DaypdfCountService daypdfCountService;

    @Autowired
    private UserSecurityService userSecurityService;

    @GetMapping("/pdf")
    public Mono<Message> getPDF(Principal principal, @RequestParam("id") String id){
        if(userSecurityService.findByPhone(principal.getName()).getType()== UserType.test){
            if(3<=daypdfCountService.todayCount(principal.getName())){
                return Mono.just(Message.ERROR("您今日已查看3次！请登录查看更多"));
            }
        }else if(userSecurityService.findByPhone(principal.getName()).getType()== UserType.GeneralAdministrator){

        }else if (userSecurityService.findByPhone(principal.getName()).getType()== UserType.GeneralPersonal){
            if(50<=daypdfCountService.todayCount(principal.getName())){
                return Mono.just(Message.ERROR("您今日已查看50次！权限已用完"));
            }
        }
        JSONObject data=new JSONObject();
        data.put("basicurl","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/pdf/");
        data.put("data",productClassService.getPDF(id));
        data.put("company",productClassService.getCompany(id));
        data.put("brand",productClassService.getBrand(id));
        daypdfCountService.add(principal.getName());
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

    @GetMapping("/seo")
    public Mono<Message> seo(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,fileService.seo()));
    }



}
