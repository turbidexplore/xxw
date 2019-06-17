package com.bangxuan.xxw.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MailLogsMapper {

    @Insert("install into mail_logs(emia,create_timel) values(#{email}, date_format(now(),'%Y-%m-%d %H:%i:%s'))")
    int add(@Param("email")String email);
}
