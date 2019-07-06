package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.*;

@Mapper
@Component
public interface UnitMapper {

    @Cacheable(cacheNames={"redis_cache"}, key = "'allubit'")
    @Select("select * from gy_sys_unit_items")
    List<JSONObject> allubit();

    @Cacheable(cacheNames={"redis_cache"}, key = "'alldatatype'")
    @Select("select * from gy_sys_datatype")
    List<JSONObject> alldatatype();

    @Cacheable(cacheNames={"redis_cache"}, key = "'codes'")
    @Select("select * from dicts where object='codes' ")
    List<JSONObject> codes();
}
