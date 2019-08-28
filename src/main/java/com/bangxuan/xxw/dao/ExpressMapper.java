package com.bangxuan.xxw.dao;

import com.bangxuan.xxw.entity.Express;
import com.bangxuan.xxw.entity.SkuInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ExpressMapper {

    @Insert("INSERT INTO gy_class_express (expressjson,classid,hasbuild,ysjson,skuinfos,skurules,bisexpressjson,maintotalcount,alltotalcount,createdate,updatedate)  " +
            " VALUES(#{expressjson},#{classid},#{hasbuild},#{ysjson},#{skuinfos},#{skurules},#{bisexpressjson},#{maintotalcount},#{alltotalcount},date_format(now(),'%Y-%m-%d %H:%i:%s'),date_format(now(),'%Y-%m-%d %H:%i:%s'))")
    int insertExpress(Express express);

    @Update("update gy_class_express set expressjson=#{expressjson},classid=#{classid},hasbuild=#{hasbuild},skurules=#{skurules},ysjson=#{ysjson},skuinfos=#{skuinfos},skurules=#{skurules},bisexpressjson=#{bisexpressjson},maintotalcount=#{maintotalcount},alltotalcount=#{alltotalcount},updatedate=date_format(now(),'%Y-%m-%d %H:%i:%s') where id=#{id}")
    int update(Express express);

    @Select("select * from gy_class_express where id = #{id} ")
    Express getOne(@Param("id")String id);

    @Select("select * from gy_class_express where classid = #{classId} ")
    Express getByClassId(@Param("classId")String classId);

}
