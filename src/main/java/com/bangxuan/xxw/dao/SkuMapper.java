package com.bangxuan.xxw.dao;

import com.bangxuan.xxw.entity.SkuInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SkuMapper {



    @Insert("insert into gy_class_skuinfo(id,classid,skuname,brandname,brandarea,brandtype,origin,unitprice,wholesaleprice,mpq,moq,qualityassurancetime,sample,zzsample,pdf,sd,td,video,logo,idx) " +
            " values(#{id},#{classid},#{skuname},#{brandname},#{brandarea},#{brandtype},#{origin},#{unitprice},#{wholesaleprice},#{mpq},#{moq},#{qualityassurancetime},#{sample},#{zzsample},#{pdf},#{sd},#{td},#{video},#{logo},#{idx})")
    int insertSKU(SkuInfo skuinfo);

    @Update("update gy_class_generalparameters set status=1,create_time=date_format(now(),'%Y-%m-%d %H:%i:%s') where id=#{id}")
    int update(SkuInfo skuinfo);

    @Select("select * from gy_class_skuinfo where id = #{id} ")
    SkuInfo getOne(@Param("id")String id);

    @Select("select * from gy_class_skuinfo where skuname like '${skuname}'  and  classid = #{classid}")
    List<SkuInfo> findBySkunameAndClassid(@Param("skuname")String skuname, @Param("classid")String classid);

    @Delete("delete from gy_class_skuinfo where classid = #{classid}")
    int deleteByClassId(@Param("classid") String classid);

    @Update("update gy_class_skuinfo set ${key}=#{value} where skuname like '${skuname}' and classid = #{classid}")
    int updatevalue(@Param("skuname") String skuname,@Param("classid") String classid,@Param("key") String key,@Param("value")  String value);

    @Update("update gy_class_skuinfo set ${key}=#{value} where  id = #{id}")
    int updateById(@Param("id") String id, @Param("key") String key,@Param("value")  String value);

    @Select("select count(*) from gy_class_skuinfo a  ${ids}")
    int countById(@Param("id")String id, @Param("ids")String ids);

    @Select("select a.* from gy_class_skuinfo a  ${ids}  order by a.idx asc  limit #{s},#{e}")
    List<SkuInfo> findByPageId(@Param("id") String id, @Param("s") Integer s, @Param("e")Integer e, @Param("ids")String ids);
}
