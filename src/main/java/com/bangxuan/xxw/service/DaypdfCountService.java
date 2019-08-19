package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.DaypafCountMapper;
import com.bangxuan.xxw.entity.DaypafCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DaypdfCountService {

    @Autowired
    private DaypafCountMapper daypafCountMapper;

    @Transactional
    public int add(String user){
        return daypafCountMapper.insert(new DaypafCount(user));
    }
}
