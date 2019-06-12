package com.bangxuan.xxw.util;

import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Component;

@Component
public class Pingyin {
    /**
     * 测试main方法
     * @param args
     */
//    public static void main(String[] args) {
//        System.out.println(transferPinyin("汉字转换为拼音")); //转为首字母大写
//
//    }
    private static Transliterator pinyinTransliterator = Transliterator
            .getInstance("Han-Latin;NFD;[[:NonspacingMark:][:Space:]] Remove");



    /**
     * 将中文转换成字母。
     * example: 正回--> ZHENGHUI
     *          "zhenghui光辉" --> ZHENGHUIGUANGHUI
     * @param formStr
     * @return
     */
    public   String transferPinyin(String formStr){
        String pinyin = pinyinTransliterator.transliterate(formStr);
        if(pinyin != null && pinyin.length() > 0){
            return pinyin.toUpperCase();
        } else {
            return "";
        }
    }
}
