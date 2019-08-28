package com.bangxuan.xxw.util;

import cn.hutool.core.util.ReUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpUtils {

    public static List<String> extractSkuExpName(String msg) {
        List<String> list = new ArrayList<String>();
        int start = 0;
        int startFlag = 0;
        int endFlag = 0;
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) == '{') {
                startFlag++;
                if (startFlag == endFlag + 1) {
                    start = i;
                }
            } else if (msg.charAt(i) == '}') {
                endFlag++;
                if (endFlag == startFlag) {
                    list.add(msg.substring(start + 1, i));
                }
            }
        }
        return list;
    }

    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("MSG21");
        list.add("");
        list.add("MSG22");

        // 获取表达式
        String skuexp = "-{MSG23}{MSG22}-{MSG27}";
        List<String> listName = ExpUtils.extractSkuExpName(skuexp);
        boolean isMatch = true;
        // 判断表达式是否和型号匹配
        for(int jj=0;jj<listName.size();jj++){
            if(StringUtils.isNotEmpty(list.get(jj))){
                if(!listName.get(jj).equals(list.get(jj))){
                    isMatch =false;
                    break;
                }
            }
        }
        System.out.println("isMatch="+isMatch);


    }
}
