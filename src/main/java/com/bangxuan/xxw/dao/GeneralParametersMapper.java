package com.bangxuan.xxw.dao;

import com.bangxuan.xxw.entity.GeneralParameters;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Component
public interface GeneralParametersMapper {

    @Transactional
    @Insert("insert into gy_class_generalparameters(id) values(#{id})")
    int install(@Param("id")String id);

    @Update("update gy_class_generalparameters set status=1,create_time=date_format(now(),'%Y-%m-%d %H:%i:%s') where id=#{id}")
    int update(GeneralParameters generalParameters);

    @Select("select * from gy_class_generalparameters where id = #{id}")
    GeneralParameters findById(@Param("id")String id);
}
