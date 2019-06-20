package com.bangxuan.xxw.entity;

import lombok.Data;
import java.util.UUID;


/**
 * 用户信息
 */
@Data
public class User {

    public User(){
        if(this.id==null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }
    }

    private String id;

    private String code;

    private String nikename;

    private String name;

    private String headportrait;

    private String city;

    private String mobile;

    private String telephone;

    private String degree;

    private String college;

    private String major;

    private String position;

    private String company_country;

    private String company_province;

    private String company_city;

    private String company_area;

    private String company_name;

    private String company_address;

    private String create_time;

    private int fkcount;

    private int ybcount;
}

