package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("select * from gy_user where code =(select user_code from gy_user_security where phonenumber=#{phone})")
    User get(@Param("phone") String phone);

    @Select("select u.name,u.headportrait,us.phonenumber from gy_user_security us,gy_user u where u.code=us.user_code and us.type ='GeneralAdministrator'  group by us.phonenumber limit 0,12")
    List<JSONObject> getAllAdminData();

    @Select("select b.name,b.headportrait,b.nikename,b.create_time,a.status,a.type,a.phonenumber " +
            " from gy_user_security a,gy_user b " +
            " where a.user_code=b.code and b.name like CONCAT('%',#{text},'%') " +
            " or a.user_code=b.code and b.nikename like CONCAT('%',#{text},'%')  " +
            " or a.user_code=b.code and a.phonenumber like CONCAT('%',#{text},'%') " +
            " order by b.create_time desc LIMIT #{page},#{size}")
    List<JSONObject> getAllByPage(@Param("page")int page, @Param("size")int size,@Param("text") String text);

    @Select("select count(*) from gy_user where code=#{code}")
    int findUserCountByCode(@Param("code") String code);

    @Select("select count(*) from gy_user where create_time like CONCAT('%',#{time},'%') ")
    int findUserCountByTime(@Param("time") String time);

    @Update("update gy_user set fkcount=#{fkcount} where code =(select user_code from gy_user_security where phonenumber=#{phone})")
    int updateFKCount(@Param("fkcount")Integer fkcount,@Param("phone")String phone);

    @Update("update gy_user set ybcount=#{ybcount} where code =(select user_code from gy_user_security where phonenumber=#{phone})")
    int updateYBCount(@Param("ybcount")Integer ybcount,@Param("phone")String phone);
}