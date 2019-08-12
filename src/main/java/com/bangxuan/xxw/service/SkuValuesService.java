package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.SkuValuesMapper;
import com.bangxuan.xxw.entity.SkuValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component
public class SkuValuesService {

    @Autowired
    private SkuValuesMapper skuValuesMapper;

    @Transactional
    public int insert(SkuValues skuValues){
        return skuValuesMapper.insertSKUValue(skuValues);
    }

    @Transactional
    public int insert(JSONObject jsonObject){
        return skuValuesMapper.insertSKUUValue(jsonObject);
    }

    @Transactional
    public int deleteByClassId(String classid){
        return skuValuesMapper.deleteByClassId(classid);
    }

    @Cacheable(cacheNames = {"redis_cache"}, key = "'skuvaluesfindById'+#skuid")
    public List<SkuValues> findById(String skuid){
        return skuValuesMapper.findBySkuId(skuid);
    }


    public List<JSONObject> findValuesByClassid(String classid){
        return skuValuesMapper.findValuesByClassid(classid);
    }
}
