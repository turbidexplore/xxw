package com.bangxuan.xxw.service;

import cn.hutool.core.io.FileUtil;
import com.bangxuan.xxw.util.CodeLib;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    @Value("${com.turbid.upload-path.images}")
    private String imagespath;

    @Autowired
    private CodeLib tencentOSS;

    public String images(MultipartFile multipartFile,String path) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
//                String[] name = multipartFile.getOriginalFilename().split("\\.");
                    File file = File.createTempFile(CodeLib.getSHC() ,"."+ FileUtil.extName(multipartFile.getOriginalFilename()));
                    multipartFile.transferTo(file);
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    String key = path+file.getName();
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
                    cosClient.shutdown();
                    CodeLib.deleteFile(file);
                    return file.getName();
            }
        }
        return "";
    }

    public String filename(Integer index,StringBuffer sb,String type) throws Exception {
        File file = null;
        file = File.createTempFile("sitemap00"+index ,type);
        CodeLib.createFile(file);

        CodeLib.writeTxtFile(sb.toString(),file);
        String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
        String key = "public/"+file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        COSClient cosClient=tencentOSS.getClient();
        PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
        cosClient.shutdown();
        return " https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/"+key;
    }


}
