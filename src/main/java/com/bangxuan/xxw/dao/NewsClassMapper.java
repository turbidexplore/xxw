package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.NewsClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NewsClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewsClass record);

    int insertSelective(NewsClass record);

    NewsClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewsClass record);

    int updateByPrimaryKey(NewsClass record);

    @Select("select * from gy_news_class where id in (1,2,3) order by class_index desc")
    List<JSONObject> all();
}