package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.*;

@Mapper
@Service
public interface AreaMapper {

    @Select("select * from gy_sys_area where parent_id=#{pid}")
    List<JSONObject> getByPid(@Param("pid")String pid);

    @Select("select * from gy_sys_area where id=#{id}")
    JSONObject getById(@Param("id")String id);
}
