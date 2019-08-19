package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CountMapper {

    @Select("select count(*) from gy_company")
    Integer companyCount();

    @Select("select count(*) from gy_company_brand")
    Integer brandCount();

    @Select("select count(*) from gy_product_class where level=1")
    Integer productClass1Count();

    @Select("select count(*) from gy_product_class where level=2")
    Integer productClass2Count();

    @Select("select count(*) from gy_product_class where level=3")
    Integer productClass3Count();

    @Select("select count(*) from gy_product_class where level=4")
    Integer productClass4Count();

    @Select("select count(*) from gy_product_class where level=5")
    Integer productClass5Count();

    @Select("select count(*) from gy_user")
    Integer userCount();

    @Select("select count(*) from gy_user where create_time like CONCAT('%',#{time},'%') ")
    Integer userCountByTime(@Param("time") String time);

    @Select("select count(*) from gy_user where create_time <=#{time}")
    Integer dayByTime(@Param("time")String time);

    @Select("select count(*) from gy_class_skuinfo ")
    Integer skuCount();

    @Select("select skuuser from gy_product_class where level=5 and skuuser is not null group by skuuser")
    List<String> getFiveClassUser();

    @Select("select count(*) from gy_product_class where skuuser=#{user} and level=5 ")
    Integer getUserCount(@Param("user") String user);

    @Select("select count(*) from gy_product_class where skuuser=#{user} and status!=2 and level=5")
    Integer getUserNotCount(@Param("user") String user);

    @Select("select count(*) from gy_product_class where skuuser=#{user} and status=2 and level=5 and day(skutime)=day(now())")
    Integer getDayUserCount(@Param("user") String user);

    @Select("select * from user_logs where userinfo ${sql} like '%-%' and date(create_time)=date(now()) group by userinfo")
    List<JSONObject> todayuserCount(@Param("sql")String sql);

    @Select("select count(*) from user_logs where day(create_time)=day(now()) ")
    Integer fwlCount();

    @Select("select count(*) from user_logs where create_time like '%${v}%' ")
    Integer mfwlCount(@Param("v")String v);

    @Select("select date(create_time) from user_logs\n" +
            "where userinfo like '%-%'  group by date(create_time) order by create_time desc")
    List<String> dates();

    @Select("select * from count_logs order by today desc")
    List<JSONObject> countlogs();

    @Insert("insert into follow_class(userinfo,classid,companyid,ip,create_time) values(#{userinfo},#{classid},#{companyid},#{ip},date_format(now(),'%Y-%m-%d %H:%i:%s'))")
    Integer addFollowClass(JSONObject jsonObject);

    @Select("select c.company_name,b.class_name,e.nikename,e.name,d.type,a.userinfo,a.ip,a.create_time\n" +
            "from follow_class a\n" +
            "left join gy_product_class b on a.classid=b.id\n" +
            "left join gy_company c on a.companyid=c.id\n" +
            "left join gy_user_security d on a.userinfo=d.phonenumber\n" +
            "left join gy_user e on d.user_code=e.code\n" +
            "where a.userinfo not like '%-%' \n" +
            "group by classid,a.userinfo,a.create_time\n" +
            "order by create_time desc")
    List<JSONObject> followInfo();

    @Select("select count(*) from follow_class ")
    Integer followInfoCount();

    @Select("select id from gy_product_class where level=5 and status=2 and skutype=2 order by id desc limit 0,1000 ")
    List<String> getaaa();
}
