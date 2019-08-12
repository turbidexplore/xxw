package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.SkuMapper;
import com.bangxuan.xxw.entity.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component
public class SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Transactional
    public int insert(SkuInfo skuInfo){
        return skuMapper.insertSKU(skuInfo);
    }

    public int update(SkuInfo skuInfo){
        return skuMapper.update(skuInfo);
    }

    @Transactional
    public int updatevalue(String skuname, String id,String key,String value){
        return skuMapper.updatevalue(skuname,id,key,value);
    }

    @Transactional
    public int deleteByClassId(String classid){
        return skuMapper.deleteByClassId(classid);
    }
    public List<SkuInfo> findById(String id){
        return skuMapper.findById(id);
    }

    @Cacheable(cacheNames = {"redis_cache"}, key = "'skufindById'+#id+'-'+#page+'-'+#size+'-'+#ids")
    public List<SkuInfo> findById(String id, Integer page, Integer size, String ids){
        return skuMapper.findByPageId(id,page*size,size,ids);
    }

    public SkuInfo getOne(String id){
        return skuMapper.getOne(id);
    }

    public List<SkuInfo> findBySkunameAndClassid(String skuname, String id) {
        return skuMapper.findBySkunameAndClassid(skuname,id);
    }

    @Transactional
    public int updateById(String id, String key, String value) {
        return skuMapper.updateById(id,key,value);
    }

    public int countById(String id,String ids) {
        return skuMapper.countById(id,ids);
    }
}
