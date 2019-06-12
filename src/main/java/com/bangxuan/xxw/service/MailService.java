package com.bangxuan.xxw.service;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

@Service
public class MailService {

    @Value("${spring.mail.account}")
    private String account;//登录用户名
    @Value("${spring.mail.pass}")
    private String pass;        //登录密码
    @Value("${spring.mail.from}")
    private String from;        //发件地址
    @Value("${spring.mail.host}")
    private String host;        //服务器地址
    @Value("${spring.mail.port}")
    private String port;        //端口
    @Value("${spring.mail.protocol}")
    private String protocol; //协议


    //用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
    static class MyAuthenricator extends Authenticator {
        String u = null;
        String p = null;

        public MyAuthenricator(String u, String p) {
            this.u = u;
            this.p = p;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(u, p);
        }
    }

    public MimeMessage mimeMessage(){
        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", protocol);
        //服务器
        prop.setProperty("mail.smtp.host", host);
        //端口
        prop.setProperty("mail.smtp.port", port);
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //使用SSL，企业邮箱必需！
        //开启安全协议
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(account, pass));
        session.setDebug(true);
        return  new MimeMessage(session);
    }

    public void send(String mail) {

        MimeMessage mimeMessage=mimeMessage();
        try {
            mimeMessage.setFrom(new InternetAddress(from, "德玛西亚"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            mimeMessage.setSubject("德玛西亚万岁");
            mimeMessage.setSentDate(new Date());
            mimeMessage.setText("您在XXX使用了密码重置功能，请点击下面链接重置密码:"
                    + "http://localhost:/XXX/ResetPassword?id=123456789");
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public void sendHtmlMail(String mail){
        try {
            String authcode= "XXX";
            MimeMessage mimeMessage=mimeMessage();
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(htmltext(authcode), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            mimeMessage.setContent(mainPart);
            mimeMessage.setFrom(new InternetAddress(from, "零件邦"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            mimeMessage.setSubject("选型就上零件邦.");
            mimeMessage.setSentDate(new Date());
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
        }catch (Exception e){

        }
    }

    private String htmltext(String authcode){
        // border:1px solid #00FF00;border-radius:8px;
        return
                        "<div style=\"width:100%;max-width: 750px;margin:0 auto;background-color: #f3f6f6\">" +
                        "  <section style=\"width:100%;margin:0 auto 20px;\">" +
                        "    <!-- <h2 class=\"section-title\">通栏banner图片</h2> -->" +
                        "    <div style=\"width:100%;\">" +
                        "      <img style=\"width:100%;\" src=\"https://www.lingjianbang.com/_nuxt/img/c38f959.jpg\" alt=\"您正在查找这样的信息吗？\">" +
                        "    </div>" +
                        "  </section>" +
                        "  <section style=\"width:100%;margin:0 auto 20px;\">" +
                        "    <h2 style=\"font-weight: bold;font-size: 18px; line-height: 32px;color: #f70;\">您在找这些零件吗？</h2>" +
                        "    <div class=\"first-list\">" +
                        "      <h4 style=\"display: block;margin:0 auto;text-align: center;font-size: 13px;line-height: 32px;color: #555;\">" +
                        "     <i>—————— </i> 轻松下载各分类零件 <i> ——————</i>" +
                        "      </h4>" +
                        "      <ul style=\"list-style: none;display: flex;margin:0;padding:0\">" +
                        "        <li style=\"flex: 1;margin-bottom:10px;list-style: none;\">" +
                        "          <div style=\"width:80%;margin:0 auto;\">" +
                        "            <img style=\"width: 100%;\" src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/201905160906357652803167307017255.png\">" +
                        "          </div>" +
                        "          <div style=\"text-align: center;\">" +
                        "            <p style=\"font-size: 14px;color:#444;\">零件分类名称</p>" +
                        "          </div>" +
                        "        </li>" +
                        "        <li style=\"flex: 1;margin-bottom:10px;list-style: none;\">" +
                        "          <div style=\"width:80%;margin:0 auto;\">" +
                        "            <img style=\"width: 100%;\" src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/201905161250001715937945422513327.png\">" +
                        "          </div>" +
                        "          <div style=\"text-align: center;\">" +
                        "            <p style=\"font-size: 14px;color:#444;\">零件分类名称</p>" +
                        "          </div>" +
                        "        </li>" +
                        "        <li style=\"flex: 1;margin-bottom:10px;list-style: none;\">" +
                        "          <div style=\"width:80%;margin:0 auto;\">" +
                        "            <img style=\"width: 100%;\" src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/product_class/images/201904260755308010803197020942958.png\">" +
                        "          </div>" +
                        "          <div style=\"text-align: center;\">" +
                        "            <p style=\"font-size: 14px;color:#444;\">零件分类名称</p>" +
                        "          </div>" +
                        "        </li>" +
                        "      </ul>" +
                        "      " +
                        "    </div>" +
                        "  </section>" +
                        "  <section style=\"width:100%;margin:0 auto 20px;\">" +
                        "    <h2 style=\"font-weight: bold;font-size: 18px; line-height: 32px;color: #f70;\">这些是品牌零件的生产商</h2>" +
                        "    <div class=\"second-list\">" +
                        "      <h4 style=\"display: block;margin:0 auto;text-align: center;font-size: 13px;line-height: 32px;color: #555;\">" +
                        "     <i>—————— </i> 已入驻的品牌 <i> ——————</i> " +
                        "      </h4>" +
                        "      <ul style=\"list-style: none;display: flex;margin:0;padding:0\">" +
                        "        <li style=\"flex: 1;margin-bottom:10px;list-style: none;\">" +
                        "          <div style=\"width:90%;margin:0 auto;\">" +
                        "            <img style=\"width: 100%;\" src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/brand/logo/1560241435748w80p1vvywud.png\">" +
                        "          </div>" +
                        "          <div style=\"width:90%;margin:0 auto;\">" +
                        "            <p style=\"position: relative;font-size: 13px;line-height: 18px;max-height: 108px;overflow: hidden;\">" +
                        "           制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业务*制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业" +
                        "            </p>" +
                        "          </div>" +
                        "          <div style=\"text-align: center;\">" +
                        "            <a style=\"display:block;width:90%;margin: 10px auto;padding: 10px 0;color:#fff;background-color: #f70;font-size:12px;text-decoration:none;\" href=\"#\">" +
                        "           跳转对应内容>>" +
                        "       </a>" +
                        "          </div>" +
                        "        </li>" +
                        "        <li style=\"flex: 1;margin-bottom:10px;list-style: none;\">" +
                        "          <div style=\"width:90%;margin:0 auto;\">" +
                        "            <img style=\"width: 100%;\" src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/brand/logo/1560231421208r77bx9e7k6.png\">" +
                        "          </div>" +
                        "          <div style=\"width:90%;margin:0 auto;\">" +
                        "            <p style=\"position: relative;font-size: 13px;line-height: 18px;max-height: 108px;overflow: hidden;\">" +
                        "           制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业务*制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业务*制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业务*" +
                        "            </p>" +
                        "          </div>" +
                        "          <div style=\"text-align: center;\">" +
                        "            <a style=\"display:block;width:90%;margin: 10px auto;padding: 10px 0;color:#fff;background-color: #f70;font-size:12px;text-decoration:none;\" href=\"#\">" +
                        "           跳转对应内容>>" +
                        "       </a>" +
                        "          </div>" +
                        "        </li>" +
                        "        <li style=\"flex: 1;margin-bottom:10px;list-style: none;\">" +
                        "          <div style=\"width:90%;margin:0 auto;\">" +
                        "            <img style=\"width: 100%;\" src=\"https://web-site-1252739071.cos.ap-shanghai.myqcloud.com/brand/logo/1560229381227yxmszo5jfba.png\">" +
                        "          </div>" +
                        "          <div style=\"width:90%;margin:0 auto;\">" +
                        "            <p style=\"position: relative;font-size: 13px;line-height: 18px;max-height: 108px;overflow: hidden;\">" +
                        "           制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业务*制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业务*制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业务*制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业务*制造、加工、经销：刃具、工具；自产产品的出口和自用产品的进口业务*" +
                        "            </p>" +
                        "          </div>" +
                        "          <div style=\"text-align: center;\">" +
                        "            <a style=\"display:block;width:90%;margin: 10px auto;padding: 10px 0;color:#fff;background-color: #f70;font-size:12px;text-decoration:none;\" href=\"#\">" +
                        "           跳转对应内容>>" +
                        "       </a>" +
                        "          </div>" +
                        "        </li>" +
                        "      </ul>" +
                        "      " +
                        "    </div>" +
                        "  </section>" +
                        "  <section style=\"width:100%;margin:0 auto 20px;\">" +
                        "    <h2 style=\"font-weight: bold;font-size: 18px; line-height: 32px;color: #f70;\">我们在不断进步!</h2>" +
                        "    <div>" +
                        "      <h4 style=\"display: block;margin:0 auto;text-align: center;font-size: 13px;line-height: 32px;color: #555;\">" +
                        "     <i>—————— </i> 轻松选型，找零件邦 <i> ——————</i> " +
                        "      </h4>" +
                        "      <hr>" +
                        "      <div>" +
                        "        <p>零件邦简介零件邦简介零件邦简介零件邦简介</p>" +
                        "        <a href=\"\">注册跳转链接</a>" +
                        "      </div>" +
                        "    </div>" +
                        "  </section>" +
                        "</div>";
    }
}
