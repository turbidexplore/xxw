package com.bangxuan.xxw.dao;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.entity.Tasks;
import lombok.Data;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
public interface TasksMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tasks record);

    int insertSelective(Tasks record);

    Tasks selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tasks record);

    int updateByPrimaryKey(Tasks record);

    @Select("select t.* from user_task t,gy_product_class p where 0<(select count(*) from gy_product_class a where  0<(select count(*) from gy_product_class b where b.id=a.pid) and a.id=p.pid) and t.taskno=p.id and t.user=#{user} and t.status='0' and t.type='logo' and t.level=#{level} order by t.create_time desc limit 0,1")
    List<Tasks> getTaskLogo(@Param("user")String user,@Param("level")String level);

    @Update("update user_task set status=1,create_time=date_format(now(),'%Y-%m-%d %H:%i:%s') where taskno=#{id}")
    int updateLogo(@Param("id") String id);

    @Select("select count(*) from user_task where user=#{user} and type='logo'")
    Integer getAllCountByUser(@Param("user")String user);

    @Select("select count(*) from user_task t,gy_product_class p where 0<(select count(*) from gy_product_class a where  0<(select count(*) from gy_product_class b where b.id=a.pid) and a.id=p.pid) and t.taskno=p.id and t.user=#{user} and t.type='logo' and t.status=#{status} and t.level=#{level}")
    Integer getStatusCount(@Param("level")String level,@Param("user")String user,@Param("status")String status);

    @Select("select count(*) from user_task where user=#{user} and type='logo' and status='1' and  to_days(create_time) = to_days(now())")
    Integer getTodayCount(@Param("user")String user);

    @Delete("delete from user_task where taskno=#{id}")
    Integer updatetg(@Param("id") String id);

    @Select("select count(*) from user_task where status='1' and type='logo'  ")
    Integer getAllCount0();

    @Select("select count(*) from gy_product_class ")
    Integer getAllCount();

    @Select("select count(*) from user_task where level=#{level} and  status='1'  and type='logo'")
    Integer getLevel(@Param("level")String level);

    @Select("select count(*) from gy_product_class where level=#{level} ")
    Integer getLevelALL(@Param("level")String level);

    @Select("select * from user_task t,gy_product_class p where  t.taskno=p.id and t.user=#{name} and t.status='1' and p.class_name like CONCAT('%',#{text},'%') or" +
            " t.taskno=p.id and t.user=#{name} and t.status='1' and p.route_path like CONCAT('%',#{text},'%') order by t.create_time desc LIMIT #{page},#{size}")
    List<JSONObject> getMy(@Param("name") String name,@Param("page") int page,@Param("size") int size,@Param("text") String text);

    @Select("select count(*) from user_task t,gy_product_class p where  t.taskno=p.id and t.user=#{name} and t.status='1'  and p.class_name like CONCAT('%',#{text},'%') ")
    int getMyCount(@Param("name")String name,@Param("text") String text);

}