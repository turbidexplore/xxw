package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    @Cacheable(cacheNames={"redis_cache"}, key = "'getbypcode_info'")
    public List<JSONObject> getByPcode() {
        return tradeMapper.getByPcode();
    }

    public List<JSONObject> getByClassPcode(String pcode) {
        return tradeMapper.getClassByPcode(pcode);
    }

    public JSONObject getByCode(String code) {
        return tradeMapper.getByCode(code);
    }

    public List<JSONObject> getByPCode(String pcode) {
        return tradeMapper.getByPCode(pcode);
    }
}
