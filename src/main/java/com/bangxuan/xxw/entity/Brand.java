package com.bangxuan.xxw.entity;

import lombok.Data;

import java.util.Date;

/**
 * 品牌
 */
@Data
public class Brand {
    private Integer id;

    private Integer company_id;

    private String brand_name;

    private Integer country_id;

    private Date add_time;

    private String add_user;

    private Byte disabled;

    private Date last_time;

    private String last_user;

    private Integer province_id;

    private Integer city_id;

    private String brand_logo;
}