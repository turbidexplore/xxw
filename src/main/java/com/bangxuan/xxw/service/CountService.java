package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.CountMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.Date;

@Service
public class CountService {

    @Autowired
    private CountMapper countMapper;

    public JSONObject count(){
        JSONObject data=new JSONObject();
        data.put("company",countMapper.companyCount());
        data.put("brand",countMapper.brandCount());
        data.put("model",countMapper.modelCount());
        data.put("pdf",countMapper.pdfCount());
        data.put("video",countMapper.videoCount());
        data.put("class1",countMapper.productClass1Count());
        data.put("class2",countMapper.productClass2Count());
        data.put("class3",countMapper.productClass3Count());
        data.put("class4",countMapper.productClass4Count());
        data.put("class5",countMapper.productClass5Count());
        data.put("user",countMapper.userCount());

        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
        data.put("duser",countMapper.userCountByTime(strNow[0]+"-"+strNow[1]+"-"+strNow[2]));
        data.put("muser",countMapper.userCountByTime(strNow[0]+"-"+strNow[1]));
        data.put("yuser",countMapper.userCountByTime(strNow[0]));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去七天
        List<String> days=new ArrayList<>();
        List<Integer> daycounts=new ArrayList<>();
        for (int i=31;i>=0;i--) {
            c.setTime(new Date());
            c.add(Calendar.DATE, -i);
            Date d = c.getTime();
            String day = format.format(d);
            days.add(day);
            daycounts.add(countMapper.dayByTime(day));
        }
        data.put("daycounts",daycounts);
        data.put("days",days);
        return data;

    }
}
