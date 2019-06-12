package com.bangxuan.xxw.service;

import com.abbyy.FREngine.*;
import com.alibaba.fastjson.JSONArray;
import com.baidu.aip.ocr.AipOcr;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.config.SamplesConfig;
import com.bangxuan.xxw.util.EexelToJson;
import com.bangxuan.xxw.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();


        URL httpurl = new URL(url);
        HttpURLConnection con = (HttpURLConnection)httpurl.openConnection();
        con .setRequestMethod("GET");
        con .setConnectTimeout(4 * 1000);
        InputStream inStream = con .getInputStream();//通过输入流获取图片数据
        byte file[] = readInputStream(inStream);

//        tableRecognitionAsync
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

//            dv.getJSONArray("footer").forEach(value->{
//                JSONObject sell=JSONObject.parseObject(value.toString());
//                if(sell.getInteger("column")==0){
//                    JSONArray row=new JSONArray();
//                    row.add(sell.getString("words"));
//                    table.add(row);
//                }else {
//                    int index=sell.getInteger("row")-1;
//                    if(index==-1){
//                        return;
//                    }
//                    JSONArray row= table.getJSONArray(index);
//                    row.add(sell.getString("words"));
//                }
//            });
            data.add(table);

        });
        return (JSONArray) data.get(0);
    }

//    public JSONObject tabledata(String requestId){
//        HashMap<String, String> options = new HashMap<String, String>();
//        options.put("result_type", "json");
//
//        // 表格识别结果
//        JSONObject res = (JSONObject) JSONObject.parse(client.tableResultGet(requestId, options).toString(2));
//        System.out.println(res.toString());
//
//
//        return data;
//    }



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
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println("AAAAAAAAAAAAAa"+response.toString());
            //获取response的body
            JSONObject res=(JSONObject) JSONObject.parse(EntityUtils.toString(response.getEntity()));
            JSONArray tables=new JSONArray();
            res.getJSONArray("prism_tablesInfo").forEach(table->{
               LinkedHashMap<String,Object> tabledata=new LinkedHashMap<>();
               JSONArray tdata=new JSONArray();
                JSONObject.parseObject(table.toString()).getJSONArray("cellInfos").forEach(cell->{
                    JSONObject celldata= JSONObject.parseObject(cell.toString());
                    System.out.println(celldata);
                    if (celldata.getInteger("xec")==0){
                        JSONArray rowdata=new JSONArray();
                        rowdata.add(celldata);
                        tdata.add(rowdata);
                        tabledata.put(celldata.getInteger("xec")+"-"+celldata.getInteger("yec"),rowdata);
                    }else {
                        JSONArray rowdata= (JSONArray) tabledata.get("0-"+celldata.getInteger("yec"));
                        if(null!=rowdata){
                            rowdata.add(celldata);

                        }
//                        else {
//                            rowdata=new JSONArray();
//                            rowdata.add(celldata);
//                            tabledata.put(celldata.getInteger("xec")+"-"+celldata.getInteger("yec"),rowdata);
//                        }

                    }

                });
                tables.add(tdata);
            });
//            mt.insert(tables,"tabledata");
            return tables;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    String baseurl="/home/alex/images/";

    public JSONArray tablex(String url){

        try {
            String path =url.split("/")[4];
            createFile(url);
            Run(path);
            return EexelToJson.excel2jsona(baseurl+path.split("\\.")[0]+".xlsx");
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }


    public void Run(String url) {

        try{
            // Load ABBYY FineReader Engine
            displayMessage( "Initializing Engine..." );
            engine = Engine.Load( SamplesConfig.GetDllFolder(), SamplesConfig.GetDeveloperSN() );
            // Process with ABBYY FineReader Engine
            // Setup FREngine
            displayMessage( "Loading predefined profile..." );
            engine.LoadPredefinedProfile( "DocumentConversion_Accuracy" );
            // Process sample image
            processImage(url);

        } catch( Exception ex ) {
            displayMessage( ex.getMessage() );
        }finally {
            // Unload ABBYY FineReader Engine
            unloadEngine();
        }
    }

    private void processImage(String url) {
        String imagePath = baseurl+url;
        try {
            // Don't recognize PDF file with a textual content, just copy it
            if( engine.IsPdfWithTextualContent( imagePath, null ) ) {
                displayMessage( "Copy results..." );
                String resultPath = SamplesConfig.GetSamplesFolder() + "\\SampleImages\\Demo_copy.pdf";
                Files.copy( Paths.get( imagePath ), Paths.get( resultPath ), StandardCopyOption.REPLACE_EXISTING );
                return;
            }
            // Create document
            IFRDocument document = engine.CreateFRDocument();

            try {

                // Add image file to document
                displayMessage( "Loading image..." );
                document.AddImageFile( imagePath, null, null );

                IDocumentProcessingParams processingParams= engine.CreateDocumentProcessingParams();
                processingParams.getPageProcessingParams().getRecognizerParams().SetPredefinedTextLanguage("English,ChinesePRC");

                // Process document
                displayMessage( "Process..." );
                document.Process( processingParams );

                // Save results
                displayMessage( "Saving results..." );

//                // Save results to rtf with default parameters
//                document.Export( baseurl+url.split("\\.")[0]+".xml", FileExportFormatEnum.FEF_XML, null);

                IXLExportParams ixlExportParams=engine.CreateXLExportParams();
                ixlExportParams.setXLFileFormat(XLFileFormatEnum.XLFF_BIFF8);
                String aExportPath =baseurl+url.split("\\.")[0]+".xlsx";
                document.Export( aExportPath, FileExportFormatEnum.FEF_XLSX, ixlExportParams);

            }catch (Exception e){
                System.out.println(e);
            }finally {
                // Close document
                document.Close();
            }
        } catch( Exception ex ) {
            displayMessage( ex.getMessage() );
        }
    }

    private void unloadEngine() {
        displayMessage( "Deinitializing Engine..." );
        engine = null;
        System.gc();
        System.runFinalization();
        Engine.Unload();
    }

    private static void displayMessage( String message ) {
        System.out.println( message );
    }

    private IEngine engine = null;


    public void createFile(String url) throws Exception {
        //new一个URL对象
        URL urla = new URL(url);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection)urla.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File(baseurl+url.split("/")[4]);
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();
    }

    public String res(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        return br.readLine();
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
}

