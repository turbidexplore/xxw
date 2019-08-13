package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.AdvertisementMapper;
import com.bangxuan.xxw.entity.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    public List<Advertisement> all(){
        return advertisementMapper.all();
    }

    public  List<Advertisement> getByPostionId(String postionId) {
        return advertisementMapper.getByPostionId(postionId);
    }
}
