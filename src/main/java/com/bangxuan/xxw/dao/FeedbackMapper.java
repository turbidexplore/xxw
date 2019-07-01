package com.bangxuan.xxw.dao;

import com.bangxuan.xxw.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FeedbackMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);

    @Select("select * from gy_feedback order by create_time desc")
    List<Feedback> selectAll();

    @Update("update gy_feedback set status=1,user=#{user},message=#{message} where id=#{id} ")
    int update(@Param("id") String id,@Param("user") String user,@Param("message") String message);
}