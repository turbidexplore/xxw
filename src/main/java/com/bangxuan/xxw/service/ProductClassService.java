package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.ProductClassMapper;
import com.bangxuan.xxw.entity.Company;
import com.bangxuan.xxw.entity.ProductClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class ProductClassService {

    @Autowired
    private ProductClassMapper productClassMapper;

    @Cacheable(cacheNames={"redis_cache"}, key = "'getproductclass_info'")
    public List<JSONObject> getProductClass(){
        return productClassMapper.selectByLevel();
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'getproductclass_infohavelogo'")
    public List<JSONObject> getProductClassHaveLogo() {
        return productClassMapper.selectByLevelHaveLogo();
    }

    public List<ProductClass> getNewTOP14(){
        return productClassMapper.selectNewTOP14();
    }

    public List<ProductClass> getByLevelCondition(ProductClass productClass) {
        return productClassMapper.selectByCondition(productClass);
    }
    public List<JSONObject> getByLevelAuth(JSONObject jsonObject) {
        return productClassMapper.selectByConditionAuth(jsonObject);
    }

    public List<ProductClass> getByText(String text) {
        return productClassMapper.selectByText(text);
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'getproductclass_allOrderPinyin'+#text")
    public List<JSONObject> allOrderPinyin(String text) {
        return productClassMapper.allOrderPinyin(text);
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'getproductclass_allOrderPinyinnumber'")
    public List<JSONObject> allNumber() {
        return productClassMapper.allNumber();
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'getproductclass_allOrderPinyiqt'")
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

    public List<JSONObject> getPDF(String id){
        return productClassMapper.getPDF(id);
    }

    public List<JSONObject> getProductClassNotLogo(String level){
        return productClassMapper.selectProductClassNotLogo(level);
    }

    public List<JSONObject> getProductClassNotLogob(String level){
        return productClassMapper.selectProductClassNotLogob(level);
    }

    public void updateLogo(String url, String id) {
        productClassMapper.updateLogo(url,id);
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

    public List<JSONObject> getByCompanyId(String companyid, Integer pid) {
        try {
            if(pid.equals(0)) {
              return  productClassMapper.getlevel1ByCompanyId(companyid);
            }else {
               int level= productClassMapper.getById(pid.toString()).getInteger("level");
               if(level==1){
                   return productClassMapper.getlevel2ByCompanyId(companyid,pid.toString());
               }else if(level==2){
                   return productClassMapper.getlevel3ByCompanyId(companyid,pid.toString());
               }else if(level==3){
                   return productClassMapper.getlevel4ByCompanyId(companyid,pid.toString());
               }else if(level==4){
                   return productClassMapper.getlevel5ByCompanyId(companyid,pid.toString());
               }

            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    public List<JSONObject> getByLevel5(String id) {
        return productClassMapper.getByLevel5(id);
    }

    public JSONObject getOne() {
        return productClassMapper.getOne();
    }

    public int updateClassData(Integer id, Integer value) {
        return productClassMapper.updateSec(value,id);
    }

    public int updateStatus(String id) {
        return productClassMapper.updateStatus(9,Integer.parseInt(id));
    }

    public int updateTaskLogo() {
        return productClassMapper.updateTaskLogo();
    }

    public List<ProductClass> all() {
        return productClassMapper.all();
    }

    public int addImage(String id, String img) {
        return productClassMapper.addImage(id,img);
    }
    public int delImage(String id) {
        return productClassMapper.delImage(id);
    }

    public int updateDesc(String desc,String id){
        return productClassMapper.updateDesc(desc,id);
    }

    public List<JSONObject> getImages(String id) {
        return productClassMapper.getImages(id);
    }
}
