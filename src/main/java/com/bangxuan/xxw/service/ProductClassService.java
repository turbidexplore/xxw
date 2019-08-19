package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.ProductClassMapper;
import com.bangxuan.xxw.entity.Company;
import com.bangxuan.xxw.entity.ProductClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component
public class ProductClassService {

    @Autowired
    private ProductClassMapper productClassMapper;

    @Cacheable(cacheNames = {"redis_cache"}, key = "'getproductclass_info'")
    public List<JSONObject> getProductClass() {
        return productClassMapper.selectByLevel();
    }

    @Cacheable(cacheNames = {"redis_cache"}, key = "'getproductclass_infohavelogo'")
    public List<JSONObject> getProductClassHaveLogo() {
        return productClassMapper.selectByLevelHaveLogo();
    }

    public List<JSONObject> getNewTOP14() {
        return productClassMapper.selectNewTOP14();
    }

    public List<ProductClass> getByLevelCondition(ProductClass productClass) {
        return productClassMapper.selectByCondition(productClass);
    }

    public List<JSONObject> getByLevelAuth(JSONObject jsonObject) {
        return productClassMapper.selectByConditionAuth(jsonObject);
    }

    public List<JSONObject> getByText(String text) {
        return productClassMapper.selectByText(text);
    }

    @Cacheable(cacheNames = {"redis_cache"}, key = "'getproductclass_allOrderPinyin'+#text")
    public List<JSONObject> allOrderPinyin(String text) {
        return productClassMapper.allOrderPinyin(text);
    }

    @Cacheable(cacheNames = {"redis_cache"}, key = "'getproductclass_allOrderPinyinnumber'")
    public List<JSONObject> allNumber() {
        return productClassMapper.allNumber();
    }

    @Cacheable(cacheNames = {"redis_cache"}, key = "'getproductclass_allOrderPinyiqt'")
    public List<JSONObject> allqt() {

        return productClassMapper.allqt();
    }

    public Integer getCount() {
        return productClassMapper.selectCount();
    }

    public Integer get3DCount() {
        return productClassMapper.select3DCount();
    }

    public Integer getPDFCount() {
        return productClassMapper.selectPDFCount();
    }

    public Integer getSKUCount() {
        return productClassMapper.selectSKUCount();
    }

    public List<JSONObject> getPDF(String id) {
        return productClassMapper.getPDF(id);
    }


    public List<JSONObject> getProductClassNotLogo(String level) {
        return productClassMapper.selectProductClassNotLogo(level);
    }

    public List<JSONObject> getProductClassNotLogob(String level) {
        return productClassMapper.selectProductClassNotLogob(level);
    }

    public void updateLogo(String url, String id) {
        productClassMapper.updateLogo(url, id);
    }

    public List<JSONObject> getByLevelHaveLogo(String id) {
        return productClassMapper.getByLevelHaveLogo(id);
    }

    public Company getCompany(String id) {
        return productClassMapper.getCompany(id);
    }

    public JSONObject getBrand(String id) {
        return productClassMapper.getBrand(id);
    }

    @Cacheable(cacheNames = {"redis_cache"}, key = "'getByCompanyId'+#companyid+#pid")
    public List<JSONObject> getByCompanyId(String companyid, Integer pid) {
        try {
            if (pid.equals(0)) {
                return productClassMapper.getlevel1ByCompanyId(companyid);
            } else {
                int level = productClassMapper.getById(pid.toString()).getInteger("level");
                if (level == 1) {
                    return productClassMapper.getlevel2ByCompanyId(companyid, pid.toString());
                } else if (level == 2) {
                    return productClassMapper.getlevel3ByCompanyId(companyid, pid.toString());
                } else if (level == 3) {
                    return productClassMapper.getlevel4ByCompanyId(companyid, pid.toString());
                } else if (level == 4) {
                    return productClassMapper.getlevel5ByCompanyId(companyid, pid.toString());
                }

            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Cacheable(cacheNames = {"redis_cache"}, key = "'getByLevel5'+#id")
    public List<JSONObject> getByLevel5(String id) {
        return productClassMapper.getByLevel5(id);
    }


    public JSONObject getOne(String user) {
        return productClassMapper.getOne(user);
    }


    public JSONObject get(String id) {
        return productClassMapper.get(id);
    }

    public int tg(Integer id, Integer value) {
        return productClassMapper.tg(value, id);
    }

    public int updateStatus(String id) {
        return productClassMapper.updateStatus(9, Integer.parseInt(id));
    }

    public List<ProductClass> all() {
        return productClassMapper.all();
    }

    public int addImage(String id, String img) {
        return productClassMapper.addImage(id, img);
    }

    public int delImage(String id) {
        return productClassMapper.delImage(id);
    }

    public int updateDesc(String desc, String id,String industry) {
        return productClassMapper.updateDesc(desc, id,industry);
    }

    @Cacheable(cacheNames = {"redis_cache"}, key = "'getImages'+#id")
    public List<JSONObject> getImages(String id) {
        return productClassMapper.getImages(id);
    }

    @Transactional
    public Integer updateSkutype(String id, Integer skutype) {
        return productClassMapper.updateSkutype(id, skutype);
    }

    public List<JSONObject> getInfos(String id, String name) {
        return productClassMapper.getInfos(id, name);
    }

    public int getFiveClass() {
        return productClassMapper.getFiveClassCount();
    }

    public List<JSONObject> myfiveclass(String name, int page, int size, String text) {
        return productClassMapper.myfiveclass(name, page * size, size, text);
    }

    public String getLogo(String id) {
        return productClassMapper.getFiveClassLogo(id);
    }

    @Transactional
    public Integer updateInfo(String id,String values) {
        return productClassMapper.updateInfo(id, values);
    }

    public int updateClassData(Integer id, Integer value) {
        return productClassMapper.updateSec(value, id);
    }

    public int getNumberclass() {
        return productClassMapper.getNumberclass();
    }

    public List<JSONObject> getByBrand(String brandid) {
        return productClassMapper.getByBrandId(brandid);
    }

    public String getEnname(String text) {
        return productClassMapper.getEnname(text);
    }

    public List<JSONObject> getByCompany(String text) {
        return productClassMapper.getByCompany(text);
    }

    public JSONObject getOneC(String name, String comid) {
        return productClassMapper.getOneC(name,comid);
    }

}
