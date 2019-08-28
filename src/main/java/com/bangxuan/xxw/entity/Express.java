package com.bangxuan.xxw.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Express {

   public Express(){

    }
//    id	int	11	0	0	-1	0	0	0		0	编号				-1	0
//    express_json	text	0	0	0	0	0	0	0		0	表达式JSON	utf8mb4	utf8mb4_general_ci		0	0
//    class_id	int	11	0	0	0	0	0	0		0	产品目录编号				0	0
//    has_build	tinyint	4	0	0	0	0	0	0	0	0	是否生成				0	0
//    ys_json	text	0	0	-1	0	0	0	0		0		utf8mb4	utf8mb4_general_ci		0	0
//    skuinfos	text	0	0	-1	0	0	0	0		0		utf8mb4	utf8mb4_general_ci		0	0
//    create_date	datetime	0	0	-1	0	0	0	0		0					0	0
//    update_date	datetime	0	0	-1	0	0	0	0		0					0	0
    private Integer id;

    private Integer classid;

    private String expressjson;

    private Integer hasbuild;

    private String ysjson;

    private String skuinfos;

    private Date createdate;

    private Date updatedate;

    private String skurules;

    private Integer maintotalcount;

    private Integer alltotalcount;

    private String bisexpressjson; // 商业参数表达式


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public String getExpressjson() {
        return expressjson;
    }

    public void setExpressjson(String expressjson) {
        this.expressjson = expressjson;
    }

    public Integer getHasbuild() {
        return hasbuild;
    }

    public void setHasbuild(Integer hasbuild) {
        this.hasbuild = hasbuild;
    }

    public String getYsjson() {
        return ysjson;
    }

    public void setYsjson(String ysjson) {
        this.ysjson = ysjson;
    }

    public String getSkuinfos() {
        return skuinfos;
    }

    public void setSkuinfos(String skuinfos) {
        this.skuinfos = skuinfos;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getSkurules() {
        return skurules;
    }

    public void setSkurules(String skurules) {
        this.skurules = skurules;
    }

    public Integer getMaintotalcount() {
        return maintotalcount;
    }

    public void setMaintotalcount(Integer maintotalcount) {
        this.maintotalcount = maintotalcount;
    }

    public Integer getAlltotalcount() {
        return alltotalcount;
    }

    public void setAlltotalcount(Integer alltotalcount) {
        this.alltotalcount = alltotalcount;
    }

    public String getBisexpressjson() {
        return bisexpressjson;
    }

    public void setBisexpressjson(String bisexpressjson) {
        this.bisexpressjson = bisexpressjson;
    }
}
