package com.bangxuan.xxw.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/***
 * 行业分类
 */
@Data
@ApiModel(value = "com.bangxuan.xxw.entity.Trade",description = "行业分类")
public class Trade {
    private Long id;



    private String name;

    private String pcode;

    private Date createtime;

    private Date updatetime;

    private Integer vision;

    private String descrition;

    public Trade(Long id, String name, String pcode, Date createtime, Date updatetime, Integer vision, String descrition) {
        this.id = id;
        this.name = name;
        this.pcode = pcode;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.vision = vision;
        this.descrition = descrition;
    }


}