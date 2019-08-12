package com.bangxuan.xxw.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MailLogsMapper {

    @Insert("insert into mail_logs(email,create_time,frommail) values(#{email}, date_format(now(),'%Y-%m-%d %H:%i:%s'),#{frommail})")
    int add(@Param("email")String email,@Param("frommail")String frommail);

    @Select("select count(*) from mail_logs where frommail = #{frommail} and TO_DAYS(create_time) = TO_DAYS(NOW())")
    Integer countByFrom(@Param("frommail")String frommail);
}
