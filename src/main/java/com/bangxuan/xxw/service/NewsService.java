package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class NewsService {

    @Autowired
    private NewsMapper newsMapper;

    public List<JSONObject> allByPID(String pid){
        return newsMapper.allByPID(pid);
    }

    public JSONObject getById(String id) {
        return newsMapper.selectById(id);
    }
}
