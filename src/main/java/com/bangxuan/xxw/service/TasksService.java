package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.TasksMapper;
import com.bangxuan.xxw.entity.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TasksService {

    @Autowired
    private TasksMapper tasksMapper;

    @Transactional
    public int insert(Tasks tasks){
        return tasksMapper.insert(tasks);
    }

    public List<Tasks> getTaskLogo(String user,String level){
        return tasksMapper.getTaskLogo(user,level);
    }

    @Transactional
    public void updateLogo(String id) {
        tasksMapper.updateLogo(id);
    }


    public Integer getAllCount(String name) {
        return tasksMapper.getAllCountByUser(name);
    }

    public Integer getStatusCount(String level,String name,String status) {
        return tasksMapper.getStatusCount(level,name,status);
    }

    public Integer getTodayCount(String name) {
        return tasksMapper.getTodayCount(name);
    }

    @Transactional
    public Integer updatetg(String id) {
        return tasksMapper.updatetg(id);
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'agetAllCount'")
    public Integer getAllCount() {
        return tasksMapper.getAllCount();
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'agetAllCount0'")
    public Integer getAllCount0() {
        return tasksMapper.getAllCount0();
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'agetLevel'+#s")
    public Integer getLevel(String s) {
        return tasksMapper.getLevel(s);
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'agetLevelALL'+#s")
    public Integer getLevelALL(String s) {
        return tasksMapper.getLevelALL(s);
    }

    public List<JSONObject> getMy(String name,int page,int size,String text) {
        return tasksMapper.getMy(name,page*size,size,text);
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'getMyCount'+#name+#text")
    public int getMyCount(String name,String text) {
        return tasksMapper.getMyCount(name,text);
    }


}
