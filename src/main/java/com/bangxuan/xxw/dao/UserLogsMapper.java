package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserLogsMapper {

    @Insert("insert into user_logs(userinfo,type,create_time) " +
            " values(#{userinfo},#{type},date_format(now(),'%Y-%m-%d %H:%i:%s'))")
    int insert(JSONObject jsonObject);
}
