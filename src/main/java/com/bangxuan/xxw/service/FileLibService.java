package com.bangxuan.xxw.service;

import com.alibaba.fastjson.JSONObject;
import com.bangxuan.xxw.dao.FileLibMapper;
import com.bangxuan.xxw.entity.FileLib;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileLibService {

    @Autowired
    private FileLibMapper fileLibMapper;

    @Transactional
    public int add(FileLib fileLib){
        return fileLibMapper.insertSelective(fileLib);
    }

    public List<JSONObject> findPage(int page, int size) {
        return fileLibMapper.findPage(page*size,size);
    }

    public int findCount() {
        return fileLibMapper.findCount();
    }
}
