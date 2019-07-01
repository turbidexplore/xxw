package com.bangxuan.xxw.service;

import com.bangxuan.xxw.entity.ProductClass;
import com.bangxuan.xxw.util.CodeLib;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileService {

    @Autowired
    private ProductClassService productClassService;

    @Value("${com.turbid.upload-path.images}")
    private String imagespath;

    @Autowired
    private CodeLib tencentOSS;

    public String images(MultipartFile multipartFile,String path) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                if ("BMP".equals(name[1]) || "JPG".equals(name[1])
                        || "JPEG".equals(name[1]) || "bmp".equals(name[1])
                        || "jpg".equals(name[1]) || "jpeg".equals(name[1])|| "PNG".equals(name[1])| "png".equals(name[1])) {
                    File file = File.createTempFile(CodeLib.getSHC() ,"."+name[1]);
                    multipartFile.transferTo(file);
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    String key = path+file.getName();
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
                    cosClient.shutdown();
                    CodeLib.deleteFile(file);
                    return " https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/"+key;
                }
            }
        }
        return "";
    }

    public String seo(){
        try {
        File file = null;
        file = File.createTempFile(CodeLib.getSHC() ,".txt");
        CodeLib.createFile(file);
            ProductClass productClass=new ProductClass();
            productClass.setLevel(Byte.valueOf("4"));
            StringBuffer sb = new StringBuffer();
        productClassService.getByLevelCondition(productClass).forEach(p->{
            sb.append("https://www.lingjianbang.com/level4/"+p.getId()+" "+CodeLib.getCurrDateStr()+" ");
        });
        CodeLib.writeTxtFile(sb.toString(),file);
        String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
        String key = "public/"+file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        COSClient cosClient=tencentOSS.getClient();
        PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
        cosClient.shutdown();
            return " https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/"+key;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
