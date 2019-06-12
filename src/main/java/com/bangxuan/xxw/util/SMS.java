package com.bangxuan.xxw.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;

public class SMS {

    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    public static int send(String phone,int mobile_code) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = {
                new NameValuePair("account", "C12472183"),
                new NameValuePair("password", "a558b2b9fb56c2ab7941653267169059"),
                //new NameValuePair("password", util.StringUtil.MD5Encode("����")),
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String SubmitResult =method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");


            if("2".equals(code)){
               return 1;
            }

        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            // Release connection
            method.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
        return 0;

    }

    public static void main(String[] ss){
        send("18914030315",1222);
    }

    public static int senda(String phone) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

        String content = new String("尊敬的尾号【"+phone.substring(7,11)+"】用户，知型网已升级为零件邦 https://www.lingjianbang.com 您可以凭本机号码可直接登录");

        NameValuePair[] data = {//�ύ����
                new NameValuePair("account", "C12472183"),
                new NameValuePair("password", "a558b2b9fb56c2ab7941653267169059"),
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);

        try {
            client.executeMethod(method);

            String SubmitResult =method.getResponseBodyAsString();

            //System.out.println(SubmitResult);

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");


            if("2".equals(code)){
                return 1;
            }

        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            // Release connection
            method.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
        return 0;

    }


}
