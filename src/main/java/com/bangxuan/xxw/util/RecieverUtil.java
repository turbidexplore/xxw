package com.bangxuan.xxw.util;

import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 收件人util 格式化发件人或者收件人,防止里面带中文字符,而导致无法发送邮件
 * 例如：字符串“xxxx@gmail.com <<xxxx@gmail.com>>,xxx@163.com <xxx@163.com>,白云
 * <xxx@qq.com>”
 * 得到的效果xxxx@gmail.com,xxx@163.com,xxx@qq.com
 * @author Administrator
 *
 */
@Component
public class RecieverUtil {

    /**
     * 获取收件人/发件人的邮箱--通过正则表达式获取尖括号内的邮箱字符串
     * 例如：字符串“xxxx@gmail.com <<xxxx@gmail.com>>,xxx@163.com <xxx@163.com>”
     * 得到的效果xxxx@gmail.com,xxx@163.com
     * @param address
     * @return
     */
    public static String formatAddressWithChinese(String address) {
        String result = "";
        String regex = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";// 匹配带尖括号的的email
        Pattern p = null;
        Matcher m = null;
        if(address != null && !"".equals(address)) {
            if(address.indexOf(",") != -1) {
                String[] add = address.split(",");
                if(add != null && add.length > 0) {
                    for(String a :add) {
                        if(a != null && !"".equals(a)) {
                            if(a.indexOf("<") != -1 && a.indexOf(">") != -1)
                                a = a.substring(a.lastIndexOf("<") + 1,a.indexOf(">")).trim();//获取最后一个“<”与第一个“>”之间的邮箱，防止出现错误数据，一个邮箱号被多个<>包含  例如：xinhulianceshi@gmail.com <<xinhulianceshi@gmail.com>>
                            p = Pattern.compile(regex);
                            m = p.matcher(a);
                            while (m.find()) {
                                result = result +  m.group() + ",";
                            }
                        }
                    }
                }
            }else {
                if(address.indexOf("<") != -1 && address.indexOf(">") != -1)
                    address = address.substring(address.lastIndexOf("<") + 1,address.indexOf(">")).trim();
                p = Pattern.compile(regex);
                m = p.matcher(address);
                while (m.find()) {
                    result = result +  m.group();
                }
            }
        }
        if(result.lastIndexOf(",") != -1)
            result = result.substring(0, result.lastIndexOf(",")).trim();
        return result;
    }

    /**
     * 获取收件人、发件人邮箱---通过javamail的InternetAddress对象来获取
     * @param address
     * @return
     */
    public static String formatAddressWithChineseByInternetAddress(String address) {
        String result = "";
        InternetAddress[] addressArray;
        try {
            addressArray = InternetAddress.parse(address);
            if(addressArray != null && addressArray.length > 0) {
                for(InternetAddress add:addressArray) {
                    result = result + add.getAddress().trim() + ",";
                }
            }
            if(result.lastIndexOf(",") != -1)
                result = result.substring(0, result.lastIndexOf(",")).trim();
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return result;
    }

}