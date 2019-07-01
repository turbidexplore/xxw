package com.bangxuan.xxw.entity;

import java.util.Date;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getVision() {
        return vision;
    }

    public void setVision(Integer vision) {
        this.vision = vision;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }
}