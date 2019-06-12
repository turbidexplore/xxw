package com.bangxuan.xxw.entity;

import lombok.Data;

@Data
public class Feedback {


    private Integer id;

    private String title;

    private String context;

    private String image;

    private String phonenumer;

    private String email;

    private Integer status;

    private String create_time;

   }