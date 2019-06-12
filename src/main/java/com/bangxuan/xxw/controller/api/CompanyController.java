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

import com.bangxuan.xxw.util.TencentOSS;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Api(value = "By Company",description = "公司")
@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BrandService brandService;

    @GetMapping("/getLogos")
    @ApiOperation("获取最新入驻匹配logo")
    public Mono<Message> getLogos(){
        JSONObject data=new JSONObject();
        data.put("basicurl","https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/brand/logo/");
        data.put("data",companyService.getLogos());
        return Mono.just(Message.SCUESSS(Message.SECUESS,data));
    }

    @GetMapping("/incomplete")
    @ApiOperation("获取信息不完整的企业信息")
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
    @ApiOperation("获取信息不完整的企业信息")
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

    @Autowired
    TencentOSS tencentOSS;

    public String getFile(String base,String value,String baseurl) throws Exception {

        URL httpurl = new URL(base+value);
        HttpURLConnection con = (HttpURLConnection)httpurl.openConnection();
        con .setRequestMethod("GET");
        con .setConnectTimeout(4 * 1000);
        InputStream inStream = con .getInputStream();//通过输入流获取图片数据
        byte file[] = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File(value);
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(file);
        //关闭输出流
        outStream.close();

        // 指定要上传到的存储桶
        String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
        // 指定要上传到 COS 上对象键
        String key = baseurl+value;

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, imageFile);
        COSClient cosClient=tencentOSS.getClient();
        PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);

        cosClient.shutdown();
        //程序结束时，删除临时文件
        deleteFile(imageFile);

        return value;
    }
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
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
    @ApiOperation("更新信息")
    public Mono<Message> update(@RequestBody Company company){
        return Mono.just(Message.SCUESSS(Message.SECUESS,companyService.update(company)));
    }

}
