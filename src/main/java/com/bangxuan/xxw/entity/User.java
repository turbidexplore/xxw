package com.bangxuan.xxw.entity;

import java.util.UUID;

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

    private String company_country_name;

    private String company_province_name;

    private String company_city_name;

    private String company_area_name;

    private String company_name;

    private String company_address;

    private String create_time;

    private int fkcount;

    private int ybcount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadportrait() {
        return headportrait;
    }

    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany_country() {
        return company_country;
    }

    public void setCompany_country(String company_country) {
        this.company_country = company_country;
    }

    public String getCompany_province() {
        return company_province;
    }

    public void setCompany_province(String company_province) {
        this.company_province = company_province;
    }

    public String getCompany_city() {
        return company_city;
    }

    public void setCompany_city(String company_city) {
        this.company_city = company_city;
    }

    public String getCompany_area() {
        return company_area;
    }

    public void setCompany_area(String company_area) {
        this.company_area = company_area;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getFkcount() {
        return fkcount;
    }

    public void setFkcount(int fkcount) {
        this.fkcount = fkcount;
    }

    public int getYbcount() {
        return ybcount;
    }

    public void setYbcount(int ybcount) {
        this.ybcount = ybcount;
    }

    public String getCompany_country_name() {
        return company_country_name;
    }

    public void setCompany_country_name(String company_country_name) {
        this.company_country_name = company_country_name;
    }

    public String getCompany_province_name() {
        return company_province_name;
    }

    public void setCompany_province_name(String company_province_name) {
        this.company_province_name = company_province_name;
    }

    public String getCompany_city_name() {
        return company_city_name;
    }

    public void setCompany_city_name(String company_city_name) {
        this.company_city_name = company_city_name;
    }

    public String getCompany_area_name() {
        return company_area_name;
    }

    public void setCompany_area_name(String company_area_name) {
        this.company_area_name = company_area_name;
    }
}

