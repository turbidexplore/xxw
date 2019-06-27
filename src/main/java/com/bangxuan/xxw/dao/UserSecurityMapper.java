package com.bangxuan.xxw.dao;

import com.bangxuan.xxw.entity.UserSecurity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserSecurityMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserSecurity record);

    int insertSelective(UserSecurity record);

    UserSecurity selectByPrimaryKey(String id);

    int updateByPhoneSelective(UserSecurity record);

    int updateByPrimaryKey(UserSecurity record);

    @Select("select count(*) from gy_user_security where phonenumber =#{phone}")
    int findCountByPhone(@Param(value = "phone") String phone);

    @Update("update gy_user_security set authcode=#{authcode},sendauthcode_time=date_format(now(),'%Y-%m-%d %H:%i:%s') where phonenumber=#{phone} ")
    int updateAuthcode(@Param(value = "phone")String phone, @Param(value = "authcode")int authcode);

    @Select("select * from gy_user_security where phonenumber =#{phone}")
    UserSecurity findByPhone(String phone);
 
    @Select("select * from gy_user_security where phonenumber =#{phonenumber} and authcode=#{authcode} and sendauthcode_time IS NOT NULL and sendauthcode_time>DATE_ADD(NOW(), INTERVAL -13 MINUTE) ")
    UserSecurity findBySMS(String phonenumber, String authcode);

    @Select("select * from gy_user_security where phonenumber =#{phonenumber} and sendauthcode_time IS NOT NULL and sendauthcode_time>DATE_ADD(NOW(), INTERVAL -13 MINUTE)")
    UserSecurity findAuthCodeByPhone(@Param("phonenumber") String phonenumber);

    @Select("select phonenumber from gy_user_security where type='GeneralAdministrator'")
    List<String> findByAdmin();

    @Select("select count(*) from gy_user_security a,gy_user b where a.user_code=b.code ")
    Integer findUserdataCount();

    @Select("select user_code from gy_user_security where phonenumber=#{phone} ")
    String findUserCodeByPhone(@Param("phone") String phone);

    @Update("update gy_user_security set password=#{password} where phonenumber=#{phone} ")
    int changePassword(@Param("phone") String phone, @Param("password") String password);

}