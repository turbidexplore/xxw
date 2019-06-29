package com.bangxuan.xxw.dao;

import com.bangxuan.xxw.entity.Company;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    @Select("select brand_logo from gy_company_brand where brand_logo !='' order by add_time desc LIMIT 0,60 ")
    List<String> selectLogos();

    @Select("select count(*) from gy_company_brand ")
    Integer selectCount();

    @Select("select * from gy_company  where status is null or status!=2 order by status asc LIMIT 0,1")
    Company getIncomplete();

    @Select("select * from gy_company where id=#{id} ")
    Company getById(@Param("id") String id);
}