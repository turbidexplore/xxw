package com.bangxuan.xxw.controller.api;


import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Message;;
import com.bangxuan.xxw.entity.values.UserType;
import com.bangxuan.xxw.service.DaypdfCountService;
import com.bangxuan.xxw.service.FileService;
import com.bangxuan.xxw.service.ProductClassService;
import com.bangxuan.xxw.service.UserSecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.Principal;

@Api(value = "By File",description = "文件")
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
    @ApiOperation("上传用户头像")
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
    @ApiOperation("上传品牌Logo")
    public Mono<Message> uploadBrandLogo(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.brandlogo(filePart)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/uploadCompanyLogo")
    @ApiOperation("上传公司Logo")
    public Mono<Message> uploadCompanyLogo(@RequestParam("file") MultipartFile filePart) {
        try {
            return Mono.just(Message.SCUESSS("上传成功", fileService.companyLogo(filePart)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/ocr")
    @ApiOperation("ocr图片存储")
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
    @ApiOperation("获取PDF")
    public Mono<Message> getPDF(Principal principal, @RequestParam("id") String id){
        if(userSecurityService.findByPhone(principal.getName()).getType()== UserType.test){
            if(3<=daypdfCountService.todayCount(principal.getName())){
                return Mono.just(Message.ERROR("您今日已查看3次！请登录查看更多"));
            }
        }else {
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
    @ApiOperation("获取PDF")
    public Mono<Message> workpdf( @RequestParam("id") String id){

        JSONObject data=new JSONObject();
        data.put("basicurl","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/pdf/");
        data.put("data",productClassService.getPDF(id));
        data.put("company",productClassService.getCompany(id));

        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/seo")
    @ApiOperation("SEO")
    public Mono<Message> seo(){
        return Mono.just(Message.SCUESSS(Message.SECUESS,fileService.seo()));
    }



}
