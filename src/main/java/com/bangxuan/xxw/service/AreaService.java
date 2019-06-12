package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AreaService {

    @Autowired
    private AreaMapper areaMapper;

    public List<JSONObject> getByPid(String pid){
        return areaMapper.getByPid(pid);
    }
}
