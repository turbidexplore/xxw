package com.bangxuan.xxw.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MailInfoMapper {

    @Insert("insert into mailinfo(text) values(#{text})")
    int install(@Param("text")String text);

    @Select("select text from mailinfo where id= (select max(id) from mailinfo)")
    String select();
}
