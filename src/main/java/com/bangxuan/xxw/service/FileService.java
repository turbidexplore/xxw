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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                    File file = File.createTempFile(CodeLib.getSHC() ,"."+name[1]);
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


    public List<String> seo(){
        try {
        List<String> filenames=new ArrayList<>();

        StringBuffer sb = new StringBuffer();
           List<ProductClass> productClassList= productClassService.getBySeo();
           int index=0;
           for (int i=0;i<productClassList.size();i++){
               if(i%40000==0&&i!=0){
                   index++;
                   filenames.add(filename(index,sb,".txt"));
                   sb = new StringBuffer();
               }
               if (i==productClassList.size()-1){
                   index++;
                   for (int a=0;a<200;a++){
                       sb.append("https://www.lingjianbang.com/news/"+a+"\n");
                   }
                   filenames.add(filename(index,sb,".txt"));
               }
               sb.append("https://www.lingjianbang.com/level"+productClassList.get(i).getLevel()+"/"+productClassList.get(i).getId()+"\n");
           }

           return filenames;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> seoxml(){
        try {
            List<String> filenames=new ArrayList<>();

            StringBuffer sb = new StringBuffer();
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><urls>");
            List<ProductClass> productClassList= productClassService.getBySeo();
            int index=0;
            for (int i=0;i<productClassList.size();i++){
                if(i%40000==0&&i!=0){
                    index++;
                    sb.append("</urls>");
                    filenames.add(filename(index,sb,".xml"));
                    sb = new StringBuffer();
                    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><urls>");
                }
                if (i==productClassList.size()-1){
                    index++;
                    for (int a=0;a<200;a++){
                        sb.append("<url>https://www.lingjianbang.com/news/"+a+"</url>\n");
                    }
                    sb.append("</urls>");
                    filenames.add(filename(index,sb,".xml"));
                }
                sb.append("<url>https://www.lingjianbang.com/level"+productClassList.get(i).getLevel()+"/"+productClassList.get(i).getId()+"</url>\n");
            }


            return filenames;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public String parseNodeToXML(List<String> treeNodes) {
        StringBuffer xmlnodes = new StringBuffer();
        if (treeNodes != null && treeNodes.size() > 0) {

            xmlnodes.append("");
            for (int i = 0; i < treeNodes.size(); i++) {
                xmlnodes.append("" + treeNodes.get(i) + "</url>");
                }

        }
        return xmlnodes.toString();
    }

}
