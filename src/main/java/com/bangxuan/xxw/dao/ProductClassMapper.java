package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Company;
import com.bangxuan.xxw.entity.ProductClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Component
public interface ProductClassMapper {


    @Select("select id,class_name,class_en,class_desc,logo,pid,level,pcount,clcount from gy_product_class where level<=3 order by class_index asc ")
    List<JSONObject> selectByLevel();

    @Select("select id,class_name,class_en,class_desc,logo,pid,level,pcount,clcount from gy_product_class\n" +
            "   where logo!='' and level=3\n" +
            "   or id in (select pid from gy_product_class where logo!='' and level=3)\n" +
            "   or id in (select pid from gy_product_class where id in (select pid from gy_product_class where logo!='' and level=3))" +
            "   order by class_index desc \n ")
    List<JSONObject> selectByLevelHaveLogo();

    @Select("select * from gy_product_class a,gy_class_pdf b\n" +
            "                         where a.id=b.class_id and a.level=5 and a.logo!='' order by a.id desc LIMIT 0,14 ")
    List<ProductClass> selectNewTOP14();

    List<ProductClass> selectByCondition(ProductClass productClass);

    @Select("select * from gy_product_class " +
            "where class_name LIKE CONCAT('%',#{text},'%') and level>=3 or class_desc LIKE CONCAT('%',#{text},'%') and level>=3 " +
            "or class_en LIKE CONCAT('%',#{text},'%') and level>=3 and level>=3 or industry LIKE CONCAT('%',#{text},'%')  and level>=3  order by pinyin_index asc ")
    List<ProductClass> selectByText(@Param("text") String text);

    @Select("select count(*) from gy_product_class where level=5")
    Integer selectCount();

    @Select("select count(*) from gy_product_sku ")
    Integer selectSKUCount();

    @Select("select count(*) from gy_class_3d ")
    Integer select3DCount();

    @Select("select count(*) from gy_class_pdf ")
    Integer selectPDFCount();

    @Select("select p.pdf_name,p.pdf_file,l.title as lang from gy_class_pdf p,gy_sys_lang l where p.lang=l.code and p.class_id = #{id}")
    List<JSONObject> getPDF(@Param("id") String id);

    @Select("select id,level from gy_product_class a\n" +
            "where 0<(select count(*) from gy_product_class b where b.pid=a.id  and b.logo !='') and\n" +
            "    (select count(*) from gy_product_class b where b.pid=a.id  and b.logo !='')=(select count(*) from gy_product_class c where c.pid=a.id )\n" +
            "    and a.level =#{level} and a.logo is null and a.id not in (select taskno from user_task) order by a.brand_id asc LIMIT 0,1\n" )
    List<JSONObject> selectProductClassNotLogo(@Param("level")String level);

    @Select("select id,level from gy_product_class a where a.level =#{level} and a.logo is null and a.id not in (select taskno from user_task) order by a.brand_id asc LIMIT 0,1 ")
    List<JSONObject> selectProductClassNotLogob(@Param("level")String level);

    @Update("update gy_product_class set logo=#{url} where id=#{id}")
    Integer updateLogo(@Param("url")String url, @Param("id")String id);

    @Select("select * from gy_product_class where logo!='' and pid = #{id}")
    List<JSONObject> getByLevelHaveLogo(@Param("id")String id);

    @Select("select b.* from gy_product_class a,gy_company b where a.company_id=b.id and a.id = #{id}")
    Company getCompany(String id);

    @Select("select b.* from gy_product_class a,gy_company_brand b where a.brand_id=b.id and a.id = #{id}")
    JSONObject getBrand(@Param("id") String id);

    @Select("select id,level,pid,logo,class_name,route_path,company_id from gy_product_class where company_id=#{companyid} and pid=#{pid}")
    List<JSONObject> getlevel5ByCompanyId(@Param("companyid") String companyid,@Param("pid") String pid);

    @Select("select id,level,pid,logo,class_name,route_path,company_id from gy_product_class d where d.id in (select e.pid from gy_product_class e where e.company_id=#{companyid} ) and d.pid=#{pid}")
    List<JSONObject> getlevel4ByCompanyId(@Param("companyid") String companyid,@Param("pid") String pid);

    @Select("select id,level,pid,logo,class_name,route_path,company_id from gy_product_class c where c.id in (select d.pid from gy_product_class d where d.id in (select e.pid from gy_product_class e where e.company_id=#{companyid} )) and c.pid=#{pid}")
    List<JSONObject> getlevel3ByCompanyId(@Param("companyid") String companyid,@Param("pid") String pid);

    @Select("select id,level,pid,logo,class_name,route_path,company_id from gy_product_class b where b.id in (select c.pid from gy_product_class c where c.id in (select d.pid from gy_product_class d where d.id in (select e.pid from gy_product_class e where e.company_id=#{companyid} ))) and b.pid=#{pid} ")
    List<JSONObject> getlevel2ByCompanyId(@Param("companyid") String companyid,@Param("pid") String pid);

    @Select("select id,level,pid,logo,class_name,route_path,company_id from gy_product_class a where a.id in (select b.pid from gy_product_class b where b.id in (select c.pid from gy_product_class c where c.id in (select d.pid from gy_product_class d where d.id in (select e.pid from gy_product_class e where e.company_id=#{companyid}))))")
    List<JSONObject> getlevel1ByCompanyId(@Param("companyid") String companyid);

    @Select("select id,level,pid,logo,class_name,route_path,company_id from gy_product_class where company_id=#{companyid}")
    List<JSONObject> getlevel5ByCompanyId1(@Param("companyid") String companyid);

    @Select("select id,level,pid,logo,class_name,route_path,company_id from gy_product_class d where d.id in (select e.pid from gy_product_class e where e.company_id=#{companyid} )")
    List<JSONObject> getlevel4ByCompanyId1(@Param("companyid") String companyid);

    @Select("select id,level,pid,logo,class_name,route_path,company_id from gy_product_class c where c.id in (select d.pid from gy_product_class d where d.id in (select e.pid from gy_product_class e where e.company_id=#{companyid} ))")
    List<JSONObject> getlevel3ByCompanyId1(@Param("companyid") String companyid);

    @Select("select id,level,pid,logo,class_name,route_path,company_id from gy_product_class b where b.id in (select c.pid from gy_product_class c where c.id in (select d.pid from gy_product_class d where d.id in (select e.pid from gy_product_class e where e.company_id=#{companyid} )))")
    List<JSONObject> getlevel2ByCompanyId1(@Param("companyid") String companyid);

    @Select("select * from gy_product_class where id =#{id}")
    JSONObject getById(@Param("id") String id);

    @Select("select a.*,b.level as auth,c.name as authname from gy_product_class a,gy_company b,dicts c where c.value=b.level and a.company_id=b.id and a.pid =#{id} and c.object='gy_company' order by c.value asc")
    List<JSONObject> getByLevel5(String id);

    @Select("select * from gy_product_class where level=4 and pinyin_index=#{text} ")
    List<JSONObject> allOrderPinyin(@Param("text") String text);

    @Select("select * from gy_product_class where level=4 and pinyin_index in ('0','1','2','3','4','5','6','7','8','9') order by pinyin_index asc ")
    List<JSONObject> allNumber();

    @Select("select * from gy_product_class where level=4 and pinyin_index not in ('0','1','2','3','4','5','6','7','8','9','A','B','C'" +
            ",'D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z') order by pinyin_index asc")
    List<JSONObject> allqt();

    @Select("select id,class_name from gy_product_class where pinyin_index is null ")
    List<JSONObject> allClass();

    @Update("update gy_product_class set pinyin_index=#{pinyin_index} where id=#{id}")
    Integer updatepinyin_index(@Param("pinyin_index")String pinyin_index, @Param("id")String id);

    List<JSONObject> selectByConditionAuth(JSONObject jsonObject);

    @Select("select a.*,b.pdf_file,c.company_addr,c.company_en,c.company_desc,c.company_name,c.logo as companylogo,c.id as company_id,d.*,e.area_name from gy_product_class a,gy_class_pdf b,gy_company c,gy_company_brand d,gy_sys_area e where e.id=d.country_id and a.company_id=c.id and a.brand_id =d.id and a.id=b.class_id and a.status <2 order by a.status,a.pinyin_index asc limit 0,1")
    JSONObject getOne();

    @Update("update gy_product_class set status=#{status} where id=#{id}")
    int updateSec(@Param("status")Integer status,@Param("id")Integer id);


}

