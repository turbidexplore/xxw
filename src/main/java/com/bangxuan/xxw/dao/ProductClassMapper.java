package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Company;
import com.bangxuan.xxw.entity.ProductClass;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

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

    @Select("select a.*,b.level as isauth,c.brand_name\n" +
            "  from gy_product_class a " +
            "  left join gy_company b on a.company_id=b.id\n" +
            "  left join gy_company_brand c on a.brand_id=c.id\n" +
            "  where a.level=5 and a.logo!='' and a.status=2\n" +
            "  group by a.company_id \n" +
            "  LIMIT 0,14")
    List<JSONObject> selectNewTOP14();

    List<ProductClass> selectByCondition(ProductClass productClass);

    @Select("select a.*,b.brand_name,c.level as isauth " +
            " from gy_product_class a,gy_company_brand b,gy_company c " +
            " where a.brand_id=b.id and a.company_id=c.id and a.class_name LIKE '%${text}%' and a.level>=3 " +
            " or a.brand_id=b.id and a.company_id=c.id and a.class_desc LIKE '%${text}%' and a.level>=3 " +
            " or a.brand_id=b.id and a.company_id=c.id and a.class_en LIKE '%${text}%' and a.level>=3 " +
            " order by a.pinyin_index asc")
    List<JSONObject> selectByText(@Param("text") String text);

    @Select("select count(*) from gy_product_class where level=5")
    Integer selectCount();

    @Select("select count(*) from gy_class_skuinfo ")
    Integer selectSKUCount();

    @Select("select count(*) from gy_class_3d ")
    Integer select3DCount();

    @Select("select count(*) from gy_class_pdf ")
    Integer selectPDFCount();

    @Select("select p.pdf_name,p.pdf_file,l.title as lang from gy_class_pdf p,gy_sys_lang l where p.lang=l.code and p.class_id = #{id}")
    List<JSONObject> getPDF(@Param("id") String id);

    @Select("select id,level from gy_product_class a where\n" +
            "   a.level =#{level} and a.logo is null and a.id not in (select taskno from user_task) order by a.brand_id asc LIMIT 0,1\n" )
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

    List<JSONObject> selectByConditionAuth(JSONObject jsonObject);

    @Select("select a.*,b.pdf_file,c.company_name,c.logo as companylogo,c.id as company_id,d.* from gy_product_class a,gy_class_pdf b,gy_company c,gy_company_brand d where a.skuuser =#{user} and a.company_id=c.id and a.brand_id =d.id and a.id=b.class_id and a.status !=2 and a.company_id!=129  order by a.status,a.company_id asc limit 0,1")
    JSONObject getOne(@Param("user")String user);

    @Select("select a.*,b.pdf_file,c.company_name,c.logo as companylogo,c.id as company_id,d.* from gy_product_class a,gy_class_pdf b,gy_company c,gy_company_brand d where a.company_id=c.id and a.brand_id =d.id and a.id=b.class_id and a.id=#{id} limit 0,1")
    JSONObject get(@Param("id")String id);

    @Update("update gy_product_class set status=#{status},skutime=date_format(now(),'%Y-%m-%d %H:%i:%s') where id=#{id}")
    int updateSec(@Param("status")Integer status,@Param("id")Integer id);

    @Update("update gy_product_class set status=#{status} where id=#{id}")
    int tg(@Param("status")Integer status,@Param("id")Integer id);

    @Update("update gy_product_class set taskstatus=#{taskstatus} where id=#{id}")
    int updateStatus(@Param("taskstatus")Integer taskstatus,@Param("id")Integer id);

    @Select("select * from gy_product_class")
    List<ProductClass> all();

    @Update("update gy_product_class set class_desc=#{desc},industry=#{industry} where id=#{id}")
    int updateDesc(@Param("desc")String desc,@Param("id") String id,@Param("industry")String industry);

    @Insert("insert into filegroup(class_id,url) values(#{class_id},#{url})")
    int addImage(@Param("class_id") String class_id,@Param("url") String url);

    @Insert("delete from filegroup where class_id=#{class_id}")
    int delImage(@Param("class_id") String class_id);

    @Select("select * from filegroup where class_id=#{id}")
    List<JSONObject> getImages(@Param("id") String id);


    @Update("update gy_product_class set skutype=#{skutype} where id=#{id}")
    int updateSkutype(@Param("id")String id,@Param("skutype")Integer skutype);

    @Select("select a.industry as hy,a.*,b.pdf_file,c.company_name,c.logo as companylogo,c.id as company_id,d.* from gy_product_class a,gy_class_pdf b,gy_company c,gy_company_brand d where a.company_id=c.id and a.brand_id =d.id and a.id=b.class_id and a.status !=2  and a.id=#{id} and a.skuuser=#{user}  and a.company_id!=129  order by a.status,a.company_id desc  ")
    List<JSONObject> getInfos(@Param("id") String id,@Param("user") String user);

    @Select("select count(*) from gy_product_class where level=5")
    int getFiveClassCount();

    @Select("select p.*,b.name,a.phonenumber,c.brand_name from gy_product_class p,gy_user_security a,gy_user b,gy_company_brand c " +
            "where p.brand_id=c.id and p.skuuser=a.phonenumber and a.user_code=b.code ${name} and p.status=2 and p.class_name like CONCAT('%',#{text},'%') or " +
            "p.brand_id=c.id and p.skuuser=a.phonenumber and a.user_code=b.code ${name} and p.status=2 and p.id like CONCAT('%',#{text},'%') or " +
            "p.brand_id=c.id and p.skuuser=a.phonenumber and a.user_code=b.code ${name} and p.status=2 and c.brand_name like CONCAT('%',#{text},'%')  " +
            "order by p.skutime desc LIMIT #{page},#{size}")
    List<JSONObject> myfiveclass(@Param("name") String name, @Param("page")int page,@Param("size") int size,@Param("text") String text);


    @Select("select logo from gy_product_class where id=#{id}")
    String getFiveClassLogo(@Param("id") String id);

    @Update("update gy_product_class set ${values} where id=#{id} ")
    Integer updateInfo(@Param("id") String id,@Param("values") String values);


    @Select("select count(*) from gy_product_class where status =2")
    int getNumberclass();

    @Select("select * from gy_product_class a,gy_company_brand b where a.brand_id =b.id and a.brand_id=#{brandid} and level=5")
    List<JSONObject> getByBrandId(@Param("brandid") String brandid);

    @Select("select en_value1 from gy_arc_cntoen where cn_key like CONCAT('%',#{text},'%') limit 0,1")
    String getEnname(@Param("text") String text);

    @Select("select a.*,b.brand_name,c.level as isauth " +
            " from gy_product_class a,gy_company_brand b,gy_company c " +
            " where  a.brand_id=b.id and a.company_id=c.id and a.industry LIKE '%${text}%'  and a.level>=3 " +
            " or a.brand_id=b.id and a.company_id=c.id and a.brand_id in(select b.id from gy_company_brand b where b.brand_name LIKE '%${text}%')  and a.level>=3 " +
            " or a.brand_id=b.id and a.company_id=c.id and a.company_id in(select c.id from gy_company c where c.company_name LIKE '%${text}%')  and a.level>=3 " +
            " order by a.pinyin_index asc")
    List<JSONObject> getByCompany(@Param("text") String text);

    @Select("select a.*,b.pdf_file,c.company_name,c.logo as companylogo,c.id as company_id,d.* from gy_product_class a,gy_class_pdf b,gy_company c,gy_company_brand d where a.skuuser =#{name} and a.company_id=c.id and a.brand_id =d.id and a.id=b.class_id and a.status !=2 and a.company_id=#{comid}   order by a.status,a.company_id asc limit 0,1")
    JSONObject getOneC(@Param("name") String name,@Param("comid") String comid);

}

