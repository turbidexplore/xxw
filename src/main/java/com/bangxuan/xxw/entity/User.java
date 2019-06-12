package com.bangxuan.xxw.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;


/**
 * 用户信息
 */
@Data
@ApiModel(value = "com.bangxuan.xxw.entity.User",description = "用户信息")
public class User {

    public User(){
        if(this.id==null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }
    }

    //用户编号
    @ApiModelProperty(value = "用户编号")
    private String id;

    //用户编码
    @ApiModelProperty(value = "用户编码")
    private String code;

    //用户昵称
    @ApiModelProperty(value = "用户昵称")
    private String nikename;

    //用户名称
    @ApiModelProperty(value = "用户名称")
    private String name;

    //用户头像
    @ApiModelProperty(value = "用户头像")
    private String headportrait;

    //用户所在城市
    @ApiModelProperty(value = "用户所在城市")
    private String city;

    //用户手机号码
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    //用户电话号码
    @ApiModelProperty(value = "手机号码")
    private String telephone;

    //用户学历
    @ApiModelProperty(value = "学历")
    private String degree;

    //毕业学校
    @ApiModelProperty(value = "毕业学校")
    private String college;

    //专业
    @ApiModelProperty(value = "专业")
    private String major;

    //职位
    @ApiModelProperty(value = "职位")
    private String position;

    //国家
    @ApiModelProperty(value = "所在公司国家")
    private String company_country;

    //省份
    @ApiModelProperty(value = "所在公司省份")
    private String company_province;

    //城市
    @ApiModelProperty(value = "所在公司城市")
    private String company_city;

    //区域
    @ApiModelProperty(value = "所在公司区域")
    private String company_area;

    //区域
    @ApiModelProperty(value = "所在公司名称")
    private String company_name;

    //区域
    @ApiModelProperty(value = "所在公司地址")
    private String company_address;

    //用户注册时间
    @ApiModelProperty(value = "用户注册时间")
    private String create_time;

    private int fkcount;

    private int ybcount;
}

