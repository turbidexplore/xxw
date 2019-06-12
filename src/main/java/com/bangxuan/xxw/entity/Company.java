package com.bangxuan.xxw.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Company {

    private Integer id;

    private String company_name;

    private String phone;

    private String pwd;

    private String salt;

    private String company_en;

    private String company_desc;

    private String company_law;

    private String company_code;

    private String company_addr;

    private Date company_createtime;

    private String company_capital;

    private String company_url;

    private String company_url1;

    private String contact_man;

    private String contact_phone;

    private String contact_phone1;

    private String contact_email;

    private String unified_code;

    private String file_3d;

    private String file_2d;

    private String video;

    private String paper_code;

    private String electron_code;

    private Byte level;

    private Date add_time;

    private String add_user;

    private Byte disabled;

    private Date last_time;

    private String last_user;

    private Integer country;

    private Integer province;

    private Integer city;

    private Integer district;

    private String logo;

    private String status;


}