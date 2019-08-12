package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
public interface BrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);

    @Select("select brand_name from gy_company_brand where id = #{id}")
    String name(@Param("id") String id);

    @Select({
            "<script>",
            "select",
            "*",
            "from gy_company_brand ",
            "where id in ",
            "<foreach item='item' index='index' collection='ids'",
            "open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"
    })
    List<JSONObject> all(@Param("ids") String[] ids);

    @Select({"<script>",
            "select a.* from gy_sys_area a,gy_company_brand b" +
                    " where a.id=b.province_id and b.country_id =3263",
            "and b.id in ",
            "<foreach item='item' index='index' collection='ids'",
            "open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>" ,
            " or a.id=b.country_id and b.country_id !=3263 ",
            "and b.id in ",
            "<foreach item='item' index='index' collection='ids'",
            "open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "group by a.area_name",
            "</script>"})
    List<JSONObject> areas(@Param("ids")String[] ids);

    @Select("select * from gy_company_brand where company_id=#{id}")
    List<Brand> getByCompanyId(@Param("id")String id);

    @Select("select id,brand_name,add_time,brand_logo from gy_company_brand where brand_logo !=''  ")
    List<JSONObject> list();

    @Select("select id,brand_name from gy_company_brand where company_id!=3")
    List<JSONObject> allBrand();

    @Select("select a.id,a.skuname from gy_class_skuinfo a " +
            "  left join gy_product_class b on a.classid=b.id " +
            "  left join gy_company_brand c on b.brand_id=c.id " +
            "  left join gy_product_class d on b.lv4=d.id " +
            "  where c.id=#{brandid} and a.skuname like '%${name}%' order by d.class_name desc limit ${page},1000")
    List<JSONObject> getClassesByBrand(@Param("name")String name,@Param("brandid")String brandid,@Param("page")Integer page);
}