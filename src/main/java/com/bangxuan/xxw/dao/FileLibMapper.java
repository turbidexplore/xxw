package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.FileLib;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FileLibMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileLib record);

    int insertSelective(FileLib record);

    FileLib selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileLib record);

    int updateByPrimaryKey(FileLib record);

    @Select("select * from gy_filelib order by create_time desc LIMIT #{page},#{size}")
    List<JSONObject> findPage(@Param("page") int page,@Param("size") int size);

    @Select("select count(*) from gy_filelib ")
    int findCount();
}