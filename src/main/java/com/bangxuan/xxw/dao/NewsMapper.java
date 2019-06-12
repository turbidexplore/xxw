package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.News;
import com.bangxuan.xxw.entity.NewsClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    @Select("select * from gy_news where class_id=#{class_id} order by add_time desc")
    List<JSONObject> allByPID(@Param("class_id")String class_id);

    @Select("select * from gy_news where id=#{id} ")
    JSONObject selectById(@Param("id")String id);
}