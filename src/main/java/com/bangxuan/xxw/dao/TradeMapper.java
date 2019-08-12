package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Trade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TradeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Trade record);

    int insertSelective(Trade record);

    Trade selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKey(Trade record);

    @Select("select id,name,pcode from sys_trade order by order_no DESC")
    List<JSONObject> getByPcode();

    @Select("select * from gy_product_class where industry =#{id} and level >3")
    List<JSONObject> getClassByPcode(@Param("id")String id);

    @Select("select a.id as a,b.id as b,c.id as c from sys_trade a,sys_trade b,sys_trade c" +
            " where a.pcode=b.id and b.pcode=c.id and a.id =#{code}")
    JSONObject getByCode(@Param("code") String code);

    @Select("select * from sys_trade where pcode =#{pcode}")
    List<JSONObject> getByPCode(@Param("pcode") String pcode);
}