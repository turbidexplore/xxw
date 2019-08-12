package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.UserLogsMapper;
import com.bangxuan.xxw.dao.UserMapper;
import com.bangxuan.xxw.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLogsMapper userLogsMapper;

    @Transactional
    public int add(User user){
        return  userMapper.insert(user);
    }

    @Transactional
    public int update(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public User get(String name) {
        return userMapper.get(name);
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'getAllAdminData'")
    public List<JSONObject> getAllAdminData() {
        return userMapper.getAllAdminData();
    }

    @Cacheable(cacheNames={"redis_cache"}, key = "'getAllByPage'+#page+#size+#text")
    public List<JSONObject> getAllByPage(int page,int size,String text) {
        return userMapper.getAllByPage(page*size,size,text);
    }

    public int findUserCountByCode(String user_code) {
        return userMapper.findUserCountByCode(user_code);
    }

    public int getUserCountByTime(String time) {
       return userMapper.findUserCountByTime(time);
    }

    public int addUserLogs(JSONObject jsonObject){
        return userLogsMapper.insert(jsonObject);
    }
}

