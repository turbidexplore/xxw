package com.bangxuan.xxw.entity;

import lombok.Data;

import java.util.Date;

@Data
public class News {
    private Integer id;

    private Integer classId;

    private String title;

    private String content;

    private String newsDesc;

    private Integer vist;

    private Byte isHot;

    private Byte isNew;

    private Date addTime;

    private String addUser;

    private Byte disabled;

    private Date lastTime;

    private String lastUser;


}