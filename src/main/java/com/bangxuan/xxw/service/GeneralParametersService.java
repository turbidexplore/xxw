package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.GeneralParametersMapper;
import com.bangxuan.xxw.entity.GeneralParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralParametersService {

    @Autowired
    private GeneralParametersMapper generalParametersMapper;

    public int install(String id){
        return generalParametersMapper.install(id);
    }

    public int update(GeneralParameters generalParameters){
        return generalParametersMapper.update(generalParameters);
    }

    public GeneralParameters findById(String id){
        return generalParametersMapper.findById(id);
    }
}
