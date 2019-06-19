package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import java.util.*;

@Mapper
@Component
public interface UnitMapper {

    @Select("select * from gy_sys_unit")
    List<JSONObject> allubit();

    @Select("select * from gy_sys_datatype")
    List<JSONObject> alldatatype();
}
