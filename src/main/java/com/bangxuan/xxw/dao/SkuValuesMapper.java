package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.SkuValues;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SkuValuesMapper {

    @Insert("insert into gy_class_skuvalues(id,classid,skuid,skukey,skuvalue,key_en,skucode,unit,datatype,vindex) values(#{id},#{classid},#{skuid},#{skukey},#{skuvalue},#{key_en},#{skucode},#{unit},#{datatype},#{index})")
    int insertSKUValue(SkuValues skuValues);

    @Insert("insert into gy_class_skuuvalues(classid,skuid,type,typedesc,val) values(#{classid},#{skuid},#{type},#{desc},#{value})")
    int insertSKUUValue(JSONObject jsonObject);

    @Select("select * from gy_class_skuvalues where skuid=#{skuid} order by vindex asc")
    List<SkuValues> findBySkuId(@Param("skuid") String skuid);

    @Delete("delete from gy_class_skuvalues where classid=#{classid}")
    int deleteByClassId(@Param("classid") String classid);

    @Select("select * from gy_class_skuvalues where classid=#{classid}  group by skukey,skuvalue,skucode                                                    order by skuvalue desc")
    List<JSONObject> findValuesByClassid(@Param("classid") String classid);
}
