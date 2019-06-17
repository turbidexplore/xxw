package com.bangxuan.xxw.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CodeLib {

    /**
     * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
     * @param request
     * @return ip
     */
    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }

    public static String MD5(String value){
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(value.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把没一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getAddressByIp(String ip) {
        if (ip == null || ip.equals("")) {
            return null;
        }
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        String thisUrl ="http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
        try {
            URL url = new URL(thisUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
            return result;

        } catch (Exception e) {
            System.out.println("获取IP地址失败");
        }
        return null;
    }

   static String[] code=new String[]{"0","1","2","3","4","5","6","7","8","9",
            "A","B","C","D","E","F","G","H","J","K","M","N","P","Q","R",
            "S","T","U","V","W","X","Y"};



    public String getNikeName(StringRedisTemplate stringRedisTemplate){

        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");

        Integer year = Integer.parseInt(strNow[0].substring(2,4));
        Integer month = Integer.parseInt(strNow[1]);
        Integer day = Integer.parseInt(strNow[2]);
        String key="c"+year+"m"+month+"d"+day;

        if(null==stringRedisTemplate.opsForValue().get(key)) {
            stringRedisTemplate.opsForValue().set(key, "1");
            return "U"+code[year]+code[month]+code[day]+"0001";
        }

        Integer value=Integer.parseInt(stringRedisTemplate.opsForValue().get(key))+1;
        String count= getCode("",value);
        System.out.println(count.length());
        if(count.length()<4){
            for (int i=0;i<=(5-count.length());i++){
                count="0"+count;
            }
        }

        stringRedisTemplate.opsForValue().set(key,value.toString());
        return "U"+code[year]+code[month]+code[day]+count;
    }

    public String getCode(String str,Integer value){
        Integer number= value/code.length;
        if(number>0){
           str= str+code[value-(number*code.length)];
            return getCode(str,number);
        }else {

            return code[value]+str;
        }


    }


}
