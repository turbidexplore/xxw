package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.MailLogsMapper;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public MimeMessage mimeMessage(){
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
        Session session = Session.getDefaultInstance(prop, new MyAuthenricator(account, pass));
        session.setDebug(true);
        return  new MimeMessage(session);
    }

    @Autowired
    private MailLogsMapper mailLogsMapper;

    public void sendHtmlMail(String mail,String context){
        try {

            MimeMessage mimeMessage=mimeMessage();
            Multipart mainPart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent(context, "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            mimeMessage.setContent(mainPart);
            mimeMessage.setFrom(new InternetAddress(from, "零件邦"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            mimeMessage.setSubject("选型就上零件邦.");
            mimeMessage.setSentDate(new Date());
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
            mailLogsMapper.add(mail);
        }catch (Exception e){
        e.printStackTrace();
        }
    }

}
