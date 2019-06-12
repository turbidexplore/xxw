package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.FeedbackMapper;
import com.bangxuan.xxw.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Transactional
    public int add(Feedback feedback){
        return feedbackMapper.insert(feedback);
    }

    public List<Feedback> getAll() {
        return feedbackMapper.selectAll();
    }

    @Transactional
    public int update(String id,String user,String text) {
        return feedbackMapper.update(id,user,text);
    }
}
