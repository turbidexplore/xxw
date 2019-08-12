package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.MailLogsMapper;
import com.bangxuan.xxw.util.RecieverUtil;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

@Service
public class MailService {

    @Value("${spring.mail.account}")
    private String account;
    @Value("${spring.mail.pass}")
    private String pass;
    @Value("${spring.mail.from}")
    private String from;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.protocol}")
    private String protocol;

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

    public MimeMessage mimeMessage(String user){
        Properties prop = new Properties();
        prop.setProperty("mail.transport.protocol", protocol);
        prop.setProperty("mail.smtp.host", host);
        prop.setProperty("mail.smtp.port", port);
        prop.setProperty("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(user, pass));
        session.setDebug(true);
        return  new MimeMessage(session);
    }

    @Autowired
    private MailLogsMapper mailLogsMapper;

    String[] froms={
            "fubei78907@163.com",
            "jiaobia69342@163.com",
            "dimutia3089345@163.com",
            "mai37856389378@163.com",
            "yan33085345907@163.com",
            "yangbei6745@163.com",
            "anqianm88908@163.com",
            "yang3419045319@163.com",
            "yidiaos964530@163.com",
            "yuyuesh152@163.com",
            "yangwen015@163.com",
            "lou234586059@163.com",
            "quling1567@163.com",
            "ji189342334231@163.com",
            "lushu190196@163.com",
            "jinlian04156@163.com",
            "shao3315907837@163.com",
            "huxiang19018@163.com",
            "lajin87267123@163.com",
            "wei904827823@163.com",
            "wei04833826055@163.com",
            "mahuang2220489@163.com",
            "guan82948160@163.com",
            "quanshe015905@163.com",
            "yinian5536056@163.com",
            "ming372604@163.com",
            "lingjianbang01@163.com",
            "catlib@163.com"
           };

    public void sendHtmlMail(String mail,String context,String f){

       from= f;
        try {
            MimeMessage mimeMessage=mimeMessage(from);
            Multipart mainPart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent(context, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            mimeMessage.setContent(mainPart);
            mimeMessage.setFrom(new InternetAddress(from, "零件邦"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(MimeUtility.encodeText(RecieverUtil.formatAddressWithChinese(mail.trim()))));
            mimeMessage.setSubject("选型就上零件邦.");
            mimeMessage.setSentDate(new Date());
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
            mailLogsMapper.add(mail,from);
        }catch (Exception e){
        e.printStackTrace();
        }
    }


}
