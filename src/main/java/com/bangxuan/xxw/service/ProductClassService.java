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

//    @Cacheable(cacheNames={"redis_cache"}, key = "'getByCompanyId'+#id")
//    public JSONArray getByCompanyId(String id) {
//        JSONArray data = new JSONArray();
//        productClassMapper.getlevel1ByCompanyId(id).forEach(level1->{
//            level1.put("data",new JSONArray());
//            productClassMapper.getlevel2ByCompanyId(id,level1.getString("id")).forEach(level2->{
//                level2.put("data",new JSONArray());
//                level1.getJSONArray("data").add(level2);
//                productClassMapper.getlevel3ByCompanyId(id,level2.getString("id")).forEach(level3->{
//                    level3.put("data",new JSONArray());
//                    level2.getJSONArray("data").add(level3);
//                    productClassMapper.getlevel4ByCompanyId(id,level3.getString("id")).forEach(level4->{
//                        level4.put("data",new JSONArray());
//                        level3.getJSONArray("data").add(level4);
//                        productClassMapper.getlevel5ByCompanyId(id,level4.getString("id")).forEach(level5->{
//                            level4.getJSONArray("data").add(level5);
//                        });
//                    });
//                });
//            });
//            data.add(level1);
//        });
//        return data;
//    }

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
//
//    @Cacheable(cacheNames={"redis_cache"}, key = "'getByCompanyId'+#id")
//    public JSONArray getByCompanyId1(String id) {
//        JSONArray data = new JSONArray();
//       List<JSONObject> a= productClassMapper.getlevel1ByCompanyId(id);
//       List<JSONObject> b= productClassMapper.getlevel2ByCompanyId1(id);
//       List<JSONObject> c= productClassMapper.getlevel3ByCompanyId1(id);
//       List<JSONObject> d= productClassMapper.getlevel4ByCompanyId1(id);
//       List<JSONObject> e= productClassMapper.getlevel5ByCompanyId1(id);
//        a.forEach(ad->{
//            ad.put("data",new JSONArray());
//            b.forEach(bd->{
//                if(ad.getString("id").equals(bd.getString("pid"))){
//                    bd.put("data",new JSONArray());
//                    ad.getJSONArray("data").add(bd);
//                    c.forEach(cd->{
//                        if(bd.getString("id").equals(cd.getString("pid"))){
//                            cd.put("data",new JSONArray());
//                            bd.getJSONArray("data").add(cd);
//                            d.forEach(dd->{
//                                if(cd.getString("id").equals(dd.getString("pid"))){
//                                    dd.put("data",new JSONArray());
//                                    cd.getJSONArray("data").add(dd);
//                                    e.forEach(ed->{
//                                        dd.getJSONArray("data").add(ed);
//                                    });
//                                }
//                            });
//                        }
//                    });
//                }
//            });
//            data.add(ad);
//        });
//        return data;
//    }

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
