package com.bangxuan.xxw.entity;

import lombok.Data;

@Data
public class DaypafCount {
    private Integer id;

    private String user;

    private String createTime;

    public DaypafCount(String user) {
        this.user = user;
    }


}