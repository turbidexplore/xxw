package com.bangxuan.xxw.entity;

import lombok.Data;

/**
 * 产品类型
 */
@Data
public class ProductClass {

    private Integer id;

    private Integer pid;

    private String class_name;

    private String class_en;

    private Byte level;

    private String route_path;

    private Integer class_index;

    private String logo;

    private String file2d;

    private String class_desc;

    private String industry;

    private String pids;

    private Integer pcount;

    private Integer clcount;

    private Integer company_id;

    private Integer brand_id;

    private String pc_position;

    private  String parent_ids;

}