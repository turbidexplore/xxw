package com.bangxuan.xxw.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Brand;
import com.bangxuan.xxw.entity.Company;
import com.bangxuan.xxw.entity.Message;
import com.bangxuan.xxw.service.BrandService;
import com.bangxuan.xxw.service.CompanyService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.bangxuan.xxw.util.CodeLib;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CodeLib tencentOSS;

    @GetMapping("/getLogos")
    public Mono<Message> getLogos(){
        JSONObject data=new JSONObject();
        data.put("basicurl","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/brand/logo/");
        data.put("data",companyService.getLogos());
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/incomplete")
    public Mono<Message> getIncomplete() throws Exception {
        JSONObject data=new JSONObject();
        Company company =companyService.getIncomplete();
        List<Brand> brands=brandService.getByCompanyId(company.getId().toString());
        if(null==company.getLogo()&&brands.size()==1&&null!=brands.get(0).getBrand_logo()){
            company.setLogo(getFile("https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/brand/logo/",brands.get(0).getBrand_logo(),"temp/"));
            companyService.update(company);
        }
        if(null!=company.getLogo()&&brands.size()==1&&null==brands.get(0).getBrand_logo()){
            brands.get(0).setBrand_logo(getFile("https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/temp/",company.getLogo(),"brand/logo/"));
            brandService.update(brands.get(0));
        }
        if (brands.size()==1&&null!=company.getCountry()&&null==brands.get(0).getCountry_id()){
            brands.get(0).setCountry_id(company.getCountry());
            brandService.update(brands.get(0));
        }
        if (brands.size()==1&&null!=company.getProvince()&&null==brands.get(0).getProvince_id()){
            brands.get(0).setProvince_id(company.getProvince());
            brandService.update(brands.get(0));
        }
        if (brands.size()==1&&null!=company.getCity()&&null==brands.get(0).getCity_id()){
            brands.get(0).setCity_id(company.getCity());
            brandService.update(brands.get(0));
        }
        data.put("brands",brands);
        data.put("company",company);

        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/get")
    public Mono<Message> get(@RequestParam("id")String id) throws Exception {
        JSONObject data=new JSONObject();
        Company company =companyService.getById(id);
        List<Brand> brands=brandService.getByCompanyId(company.getId().toString());
        if(null==company.getLogo()&&brands.size()==1&&null!=brands.get(0).getBrand_logo()){
            company.setLogo(getFile("https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/brand/logo/",brands.get(0).getBrand_logo(),"temp/"));
            companyService.update(company);
        }
        if(null!=company.getLogo()&&brands.size()==1&&null==brands.get(0).getBrand_logo()){
            brands.get(0).setBrand_logo(getFile("https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/temp/",company.getLogo(),"brand/logo/"));
            brandService.update(brands.get(0));
        }
        if (brands.size()==1&&null!=company.getCountry()&&null==brands.get(0).getCountry_id()){
            brands.get(0).setCountry_id(company.getCountry());
            brandService.update(brands.get(0));
        }
        if (brands.size()==1&&null!=company.getProvince()&&null==brands.get(0).getProvince_id()){
            brands.get(0).setProvince_id(company.getProvince());
            brandService.update(brands.get(0));
        }
        if (brands.size()==1&&null!=company.getCity()&&null==brands.get(0).getCity_id()){
            brands.get(0).setCity_id(company.getCity());
            brandService.update(brands.get(0));
        }
        data.put("brands",brands);
        data.put("company",company);

        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }


    public String getFile(String base,String value,String baseurl) throws Exception {
        URL httpurl = new URL(base+value);
        HttpURLConnection con = (HttpURLConnection)httpurl.openConnection();
        con .setRequestMethod("GET");
        con .setConnectTimeout(4 * 1000);
        InputStream inStream = con .getInputStream();
        byte file[] = readInputStream(inStream);
        File imageFile = new File(value);
        FileOutputStream outStream = new FileOutputStream(imageFile);
        outStream.write(file);
        outStream.close();
        String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
        String key = baseurl+value;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, imageFile);
        COSClient cosClient=tencentOSS.getClient();
        PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
        cosClient.shutdown();
        deleteFile(imageFile);
        return value;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @PutMapping(value = "/update")
    public Mono<Message> update(@RequestBody Company company){
        return Mono.just(Message.SCUESSS(Message.SECUESS,companyService.update(company)));
    }

}
