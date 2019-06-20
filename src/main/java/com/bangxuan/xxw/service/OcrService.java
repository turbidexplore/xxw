package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONArray;
import com.baidu.aip.ocr.AipOcr;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.util.CodeLib;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class OcrService {

    @Autowired
    private AipOcr client;

    public JSONObject word(String url){
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");


        // 通用文字识别, 图片参数为远程url图片
        JSONObject res = (JSONObject) JSONObject.parse(client.basicGeneralUrl(url, options).toString(2));
        System.out.println(res.toString());
        return  res;
    }

    public JSONArray table(String url) throws Exception {
        HashMap<String, String> options = new HashMap<String, String>();
        URL httpurl = new URL(url);
        HttpURLConnection con = (HttpURLConnection)httpurl.openConnection();
        con .setRequestMethod("GET");
        con .setConnectTimeout(4 * 1000);
        InputStream inStream = con .getInputStream();//通过输入流获取图片数据
        byte file[] = readInputStream(inStream);

        JSONObject res = (JSONObject) JSONObject.parse(client.form(file, options).toString(2));
        System.out.println(res.toString());
        JSONArray result=res.getJSONArray("forms_result");
        JSONArray data = new JSONArray();
        result.forEach(d->{
            JSONArray table=new JSONArray();
            JSONObject dv=JSONObject.parseObject(d.toString());
            dv.getJSONArray("body").forEach(value->{
                JSONObject sell=JSONObject.parseObject(value.toString());
                if(sell.getInteger("column")==0){
                    JSONArray row=new JSONArray();
                    row.add(sell.getString("words"));
                    table.add(row);
                }else {
                    int index=sell.getInteger("row")-1;
                    if(index==-1){
                        return;
                    }
                    JSONArray row= table.getJSONArray(index);
                    row.add(sell.getString("words"));
                }
            });

            data.add(table);

        });
        return (JSONArray) data.get(0);
    }

    @Autowired
    private MongoTemplate mt; //自动注入MongoTemplate

    public JSONArray table1(String url){
        try {
            String host = "https://ocrapi-advanced.taobao.com";
            String path = "/ocrservice/advanced";
            String method = "POST";
            String appcode = "f9252db4e7c440adbbd25ed394680edc";
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            //根据API的要求，定义相对应的Content-Type
            headers.put("Content-Type", "application/json; charset=UTF-8");
            Map<String, String> querys = new HashMap<String, String>();
            String bodys = "{\"url\":\""+url+"\",\"prob\":false,\"charInfo\":false,\"rotate\":false,\"table\":true}";
            HttpResponse response = CodeLib.doPost(host, path, method, headers, querys, bodys);
            System.out.println("AAAAAAAAAAAAAa"+response.toString());
            //获取response的body
            JSONObject res=(JSONObject) JSONObject.parse(EntityUtils.toString(response.getEntity()));
            JSONArray tables=new JSONArray();
            res.getJSONArray("prism_tablesInfo").forEach(table->{
               LinkedHashMap<String,Object> tabledata=new LinkedHashMap<>();
                JSONObject.parseObject(table.toString()).getJSONArray("cellInfos").forEach(cell->{
                    JSONObject celldata= JSONObject.parseObject(cell.toString());
                    System.out.println(celldata);
                    if (celldata.getInteger("xec")==0){
                        JSONArray rowdata=new JSONArray();
                        rowdata.add(celldata);
                        tables.add(rowdata);
                        tabledata.put(celldata.getInteger("xec")+"-"+celldata.getInteger("yec"),rowdata);
                    }else {
                        JSONArray rowdata= (JSONArray) tabledata.get("0-"+celldata.getInteger("yec"));
                        if(null!=rowdata){
                            rowdata.add(celldata);

                        }
                    }

                });

            });
            return tables;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String res(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine();
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
}

