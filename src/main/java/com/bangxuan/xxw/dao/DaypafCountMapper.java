package com.bangxuan.xxw.dao;

import com.bangxuan.xxw.entity.DaypafCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DaypafCountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DaypafCount record);

    int insertSelective(DaypafCount record);

    DaypafCount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DaypafCount record);

    int updateByPrimaryKey(DaypafCount record);
}