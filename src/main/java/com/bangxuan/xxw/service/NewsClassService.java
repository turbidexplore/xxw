package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.NewsClassMapper;
import com.bangxuan.xxw.entity.NewsClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class NewsClassService {

    @Autowired
    private NewsClassMapper newsClassMapper;

    public List<JSONObject> all(){
        return newsClassMapper.all();
    }
}
