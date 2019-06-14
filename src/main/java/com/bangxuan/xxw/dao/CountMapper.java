package com.bangxuan.xxw.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CountMapper {

    @Select("select count(*) from gy_company")
    Integer companyCount();

    @Select("select count(*) from gy_company where level=#{level}")
    Integer companyCountByLevel(@Param("level") String level);

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

    @Select("select count(*) from gy_class_pdf")
    Integer pdfCount();

    @Select("select count(*) from gy_class_video")
    Integer videoCount();

    @Select("select count(*) from gy_class_3d")
    Integer modelCount();

    @Select("select count(*) from gy_user where create_time <=#{time}")
    Integer dayByTime(@Param("time")String time);



}
