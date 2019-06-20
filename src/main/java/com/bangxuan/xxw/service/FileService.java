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

    @Value("${com.turbid.upload-path.images}")
    private String imagespath;

    @Autowired
    private CodeLib tencentOSS;

    private String basicurl=" https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/";

    public String images(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                if ("BMP".equals(name[1]) || "JPG".equals(name[1])
                        || "JPEG".equals(name[1]) || "bmp".equals(name[1])
                        || "jpg".equals(name[1]) || "jpeg".equals(name[1])|| "PNG".equals(name[1])| "png".equals(name[1])) {
                    File file = File.createTempFile(CodeLib.getSHC() ,"."+name[1]);
                    multipartFile.transferTo(file);
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    String key = "public/"+file.getName();
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
                    cosClient.shutdown();
                    deleteFile(file);
                    return basicurl+key;
                }
            }
        }
        return "";
    }

    public Object logo(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                if ("BMP".equals(name[1]) || "JPG".equals(name[1])
                        || "JPEG".equals(name[1]) || "bmp".equals(name[1])
                        || "jpg".equals(name[1]) || "jpeg".equals(name[1])|| "PNG".equals(name[1])| "png".equals(name[1])) {
                    File file = File.createTempFile(CodeLib.getSHC() ,"."+name[1]);
                   String filename=file.getName();
                    multipartFile.transferTo(file);
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    String key = "product_class/images/"+filename;
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
                    cosClient.shutdown();
                    deleteFile(file);
                    return filename;
                }
            }
        }
        return "";
    }

    public Object brandlogo(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                if ("BMP".equals(name[1]) || "JPG".equals(name[1])
                        || "JPEG".equals(name[1]) || "bmp".equals(name[1])
                        || "jpg".equals(name[1]) || "jpeg".equals(name[1])|| "PNG".equals(name[1])| "png".equals(name[1])) {
                    File file = File.createTempFile(CodeLib.getSHC() ,"."+name[1]);
                    String filename=file.getName();
                    multipartFile.transferTo(file);
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    String key = "brand/logo/"+filename;
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
                    cosClient.shutdown();
                    deleteFile(file);
                    return filename;
                }
            }
        }
        return "";
    }

    public Object companyLogo(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                if ("BMP".equals(name[1]) || "JPG".equals(name[1])
                        || "JPEG".equals(name[1]) || "bmp".equals(name[1])
                        || "jpg".equals(name[1]) || "jpeg".equals(name[1])|| "PNG".equals(name[1])| "png".equals(name[1])) {

                    File file = File.createTempFile(CodeLib.getSHC() ,"."+name[1]);
                    String filename=file.getName();
                    multipartFile.transferTo(file);
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    String key = "temp/"+filename;
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
                    cosClient.shutdown();
                    deleteFile(file);
                    return filename;
                }
            }
        }
        return "";
    }

    public Object ocr(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                if ("BMP".equals(name[1]) || "JPG".equals(name[1])
                        || "JPEG".equals(name[1]) || "bmp".equals(name[1])
                        || "jpg".equals(name[1]) || "jpeg".equals(name[1])|| "PNG".equals(name[1])| "png".equals(name[1])) {
                    File file = File.createTempFile(CodeLib.getSHC() ,"."+name[1]);
                    String filename=file.getName();
                    multipartFile.transferTo(file);
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    String key = "ocr/"+filename;
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
                    cosClient.shutdown();
                    deleteFile(file);
                    return filename;
                }
            }
        }
        return "";
    }

    public Object imagesUserHead(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                if ("BMP".equals(name[1]) || "JPG".equals(name[1])
                        || "JPEG".equals(name[1]) || "bmp".equals(name[1])
                        || "jpg".equals(name[1]) || "jpeg".equals(name[1])|| "PNG".equals(name[1])| "png".equals(name[1])) {
                    File file = File.createTempFile(CodeLib.getSHC() ,"."+name[1]);
                    String filename=file.getName();
                    multipartFile.transferTo(file);
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    String key = "userinfo/"+filename;
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
                    cosClient.shutdown();
                    deleteFile(file);
                    return basicurl+key;
                }
            }
        }
        return "";
    }

    public String filelib(MultipartFile multipartFile) throws IOException {

        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                    File file = File.createTempFile(CodeLib.getSHC() ,"."+name[1]);
                    multipartFile.transferTo(file);
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    String key = "filelib/"+file.getName();
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
                    cosClient.shutdown();
                    deleteFile(file);
                    return basicurl+key;
            }
        }
        return "";
    }

    @Autowired
    private ProductClassService productClassService;

    public String seo(){
        try {
        File file = null;
        file = File.createTempFile(CodeLib.getSHC() ,".txt");
        FileService.createFile(file);
            ProductClass productClass=new ProductClass();
            productClass.setLevel(Byte.valueOf("4"));
            StringBuffer sb = new StringBuffer();
        productClassService.getByLevelCondition(productClass).forEach(p->{
            sb.append("https://www.lingjianbang.com/level4/"+p.getId()+" "+CodeLib.getCurrDateStr()+" ");
        });
        FileService.writeTxtFile(sb.toString(),file);
        String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
        String key = "public/"+file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        COSClient cosClient=tencentOSS.getClient();
        PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);
        cosClient.shutdown();
            return basicurl+key;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static boolean createFile(File fileName)throws Exception{
        boolean flag=false;
        try{
            if(!fileName.exists()){
                fileName.createNewFile();
                flag=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }


    public static boolean writeTxtFile(String content,File  fileName)throws Exception{
        RandomAccessFile mm=null;
        boolean flag=false;
        FileOutputStream o=null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("GBK"));
            o.close();
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(mm!=null){
                mm.close();
            }
        }
        return flag;
    }


    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
