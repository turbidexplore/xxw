package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.ExpressMapper;
import com.bangxuan.xxw.dao.SkuMapper;
import com.bangxuan.xxw.entity.Express;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class ExpressService {

    @Autowired
    private ExpressMapper expressMapper;

    public void insert(Express express) {
        expressMapper.insertExpress(express);
    }
    public Express getByClassId(String classId){
        return expressMapper.getByClassId(classId);
    }

    public void update(Express express) {
        this.expressMapper.update(express);
    }
}
