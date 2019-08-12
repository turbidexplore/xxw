package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.BrandMapper;
import com.bangxuan.xxw.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public String name(String id){
        return brandMapper.name(id);
    }

    public List<JSONObject> all(String[] ids) {
        return brandMapper.all(ids);
    }

    public List<JSONObject> areas(String[] ids) {
        return brandMapper.areas(ids);
    }

    public List<Brand> getByCompanyId(String id) {
        return brandMapper.getByCompanyId(id);
    }

    @Transactional
    public int update(Brand brand) {
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    public List<JSONObject> list() {
        return brandMapper.list();
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'allBrandInfos'")
    public List<JSONObject> allBrand() {
        return brandMapper.allBrand();
    }

    public List<JSONObject> getClassesByBrand(String name,String brandid,Integer page){
        return brandMapper.getClassesByBrand(name,brandid,page);
    }
}
