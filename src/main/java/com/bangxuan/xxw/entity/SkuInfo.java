package com.bangxuan.xxw.entity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class SkuInfo {

   public SkuInfo(){
        this.skuValuesList=new ArrayList<>();
    }

    private List<SkuValues> skuValuesList;

    public List<SkuValues> getSkuValuesList() {
        return skuValuesList;
    }

    public void setSkuValuesList(List<SkuValues> skuValuesList) {
        this.skuValuesList = skuValuesList;
    }

    private String id;

    private Integer classid;

    private String skuname;

    private String brandname;

    private String brandarea;

    private String brandtype;

    private String origin;

    private String unitprice;

    private String wholesaleprice;

    private String mpq;

    private String moq;

    private String qualityassurancetime;

    private String sample;

    private String zzsample;

    private String pdf;

    private String sd;

    private String td;

    private String video;

    private String logo;

    private Integer idx;

    private Integer isauth;

    private String skunameexp;

    public Integer getIsauth() {
        return isauth;
    }

    public void setIsauth(Integer isauth) {
        this.isauth = isauth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getBrandarea() {
        return brandarea;
    }

    public void setBrandarea(String brandarea) {
        this.brandarea = brandarea;
    }

    public String getBrandtype() {
        return brandtype;
    }

    public void setBrandtype(String brandtype) {
        this.brandtype = brandtype;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getWholesaleprice() {
        return wholesaleprice;
    }

    public void setWholesaleprice(String wholesaleprice) {
        this.wholesaleprice = wholesaleprice;
    }

    public String getMpq() {
        return mpq;
    }

    public void setMpq(String mpq) {
        this.mpq = mpq;
    }

    public String getMoq() {
        return moq;
    }

    public void setMoq(String moq) {
        this.moq = moq;
    }

    public String getQualityassurancetime() {
        return qualityassurancetime;
    }

    public void setQualityassurancetime(String qualityassurancetime) {
        this.qualityassurancetime = qualityassurancetime;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getZzsample() {
        return zzsample;
    }

    public void setZzsample(String zzsample) {
        this.zzsample = zzsample;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getTd() {
        return td;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getSkunameexp() {
        return skunameexp;
    }

    public void setSkunameexp(String skunameexp) {
        this.skunameexp = skunameexp;
    }
}
