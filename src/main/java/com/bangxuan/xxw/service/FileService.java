package com.bangxuan.xxw.service;

import com.bangxuan.xxw.entity.ProductClass;
import com.bangxuan.xxw.util.DateDealwith;
import com.bangxuan.xxw.util.TencentOSS;
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
    private TencentOSS tencentOSS;

    private String basicurl=" https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/";

    public String images(MultipartFile multipartFile) throws IOException {

        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                if ("BMP".equals(name[1]) || "JPG".equals(name[1])
                        || "JPEG".equals(name[1]) || "bmp".equals(name[1])
                        || "jpg".equals(name[1]) || "jpeg".equals(name[1])|| "PNG".equals(name[1])| "png".equals(name[1])) {

                    // 物理地址
                    File file = File.createTempFile(DateDealwith.getSHC() ,"."+name[1]);
                    multipartFile.transferTo(file);
                    // 指定要上传到的存储桶
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    // 指定要上传到 COS 上对象键
                    String key = "public/"+file.getName();

                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);

                    cosClient.shutdown();
                    //程序结束时，删除临时文件
                    deleteFile(file);
                    return basicurl+key;
                }
            }
        }
        return "";
    }

    /**
     * 删除
     *
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public Object logo(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null || "".equals(multipartFile.getOriginalFilename())) {
                String[] name = multipartFile.getOriginalFilename().split("\\.");
                if ("BMP".equals(name[1]) || "JPG".equals(name[1])
                        || "JPEG".equals(name[1]) || "bmp".equals(name[1])
                        || "jpg".equals(name[1]) || "jpeg".equals(name[1])|| "PNG".equals(name[1])| "png".equals(name[1])) {
                    // 物理地址
                    File file = File.createTempFile(DateDealwith.getSHC() ,"."+name[1]);
                   String filename=file.getName();
                    multipartFile.transferTo(file);
                    // 指定要上传到的存储桶
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    // 指定要上传到 COS 上对象键
                    String key = "product_class/images/"+filename;

                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);

                    cosClient.shutdown();
                    //程序结束时，删除临时文件
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

                    // 物理地址
                    File file = File.createTempFile(DateDealwith.getSHC() ,"."+name[1]);
                    String filename=file.getName();
                    multipartFile.transferTo(file);
                    // 指定要上传到的存储桶
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    // 指定要上传到 COS 上对象键
                    String key = "brand/logo/"+filename;

                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);

                    cosClient.shutdown();
                    //程序结束时，删除临时文件
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

                    // 物理地址
                    File file = File.createTempFile(DateDealwith.getSHC() ,"."+name[1]);
                    String filename=file.getName();
                    multipartFile.transferTo(file);
                    // 指定要上传到的存储桶
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    // 指定要上传到 COS 上对象键
                    String key = "temp/"+filename;

                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);

                    cosClient.shutdown();
                    //程序结束时，删除临时文件
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

                    // 物理地址
                    File file = File.createTempFile(DateDealwith.getSHC() ,"."+name[1]);
                    String filename=file.getName();
                    multipartFile.transferTo(file);
                    // 指定要上传到的存储桶
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    // 指定要上传到 COS 上对象键
                    String key = "ocr/"+filename;

                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);

                    cosClient.shutdown();
                    //程序结束时，删除临时文件
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

                    // 物理地址
                    File file = File.createTempFile(DateDealwith.getSHC() ,"."+name[1]);
                    String filename=file.getName();
                    multipartFile.transferTo(file);
                    // 指定要上传到的存储桶
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    // 指定要上传到 COS 上对象键
                    String key = "userinfo/"+filename;

                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);

                    cosClient.shutdown();
                    //程序结束时，删除临时文件
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
                    // 物理地址
                    File file = File.createTempFile(DateDealwith.getSHC() ,"."+name[1]);
                    multipartFile.transferTo(file);
                    // 指定要上传到的存储桶
                    String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
                    // 指定要上传到 COS 上对象键
                    String key = "filelib/"+file.getName();

                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
                    COSClient cosClient=tencentOSS.getClient();
                    PutObjectResult putObjectResult =cosClient .putObject(putObjectRequest);

                    cosClient.shutdown();
                    //程序结束时，删除临时文件
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

        file = File.createTempFile(DateDealwith.getSHC() ,".txt");

        FileService.createFile(file);
            ProductClass productClass=new ProductClass();
            productClass.setLevel(Byte.valueOf("4"));
            StringBuffer sb = new StringBuffer();
        productClassService.getByLevelCondition(productClass).forEach(p->{
            sb.append("https://www.lingjianbang.com/level4/"+p.getId()+" "+DateDealwith.getCurrDateStr()+" ");
        });
        FileService.writeTxtFile(sb.toString(),file);

        // 指定要上传到的存储桶
        String bucketName =tencentOSS.QCLOUD_FILE_BUCKET;
        // 指定要上传到 COS 上对象键
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



    /**
     * 创建文件
     * @param fileName
     * @return
     */
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

    /**
     * 读TXT文件内容
     * @param fileName
     * @return
     */
    public static String readTxtFile(File fileName)throws Exception{
        String result=null;
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        try{
            fileReader=new FileReader(fileName);
            bufferedReader=new BufferedReader(fileReader);
            try{
                String read=null;
                while((read=bufferedReader.readLine())!=null){
                    result=result+read+"\r\n";
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(fileReader!=null){
                fileReader.close();
            }
        }
        System.out.println("读取出来的文件内容是："+"\r\n"+result);
        return result;
    }


    public static boolean writeTxtFile(String content,File  fileName)throws Exception{
        RandomAccessFile mm=null;
        boolean flag=false;
        FileOutputStream o=null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("GBK"));
            o.close();
//   mm=new RandomAccessFile(fileName,"rw");
//   mm.writeBytes(content);
            flag=true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            if(mm!=null){
                mm.close();
            }
        }
        return flag;
    }



    public static void contentToTxt(String filePath, String content) {
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
