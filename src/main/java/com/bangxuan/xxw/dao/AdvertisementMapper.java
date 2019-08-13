package com.bangxuan.xxw.dao;

import com.bangxuan.xxw.entity.Advertisement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    int insertSelective(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);

    @Select("select * from gy_ad")
    List<Advertisement> all();

    @Select("select * from gy_ad where pc_position=#{postionId}")
    List<Advertisement> getByPostionId(String postionId);
}