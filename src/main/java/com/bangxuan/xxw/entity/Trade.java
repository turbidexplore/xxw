package com.bangxuan.xxw.entity;

import lombok.Data;

import java.util.Date;

@Data
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