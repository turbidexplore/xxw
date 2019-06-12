package com.bangxuan.xxw.entity;

import com.bangxuan.xxw.entity.values.ThirdPartyTypeOfAuth;
import com.bangxuan.xxw.entity.values.UserStatus;
import com.bangxuan.xxw.entity.values.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;

/**
 * 用户安全信息
 */
@ApiModel("用户安全信息")
@Data
public class UserSecurity {

    public UserSecurity(){
        if(this.id==null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
            this.user_code = UUID.randomUUID().toString().replace("-", "");
        }
    }

    //安全编号
    @ApiModelProperty(value = "安全编号")
    private String id;

    //用户编码
    @ApiModelProperty(value = "用户编码")
    private String user_code;

    //用户密码
    @ApiModelProperty(value = "用户密码")
    private String password;

    //盐值
    @ApiModelProperty(value = "盐值")
    private String saltvalue;

    //用户手机号码
    @ApiModelProperty(value = "用户手机号码")
    private String phonenumber;

    //用户邮箱地址
    @ApiModelProperty(value = "用户邮箱地址")
    private String email;

    //授权码
    @ApiModelProperty(value = "授权码")
    private String authcode;

    //授权码最新发送时间
    @ApiModelProperty(value = "授权码最新发送时间")
    private String sendauthcode_time;

    //第三方授权类型
    @ApiModelProperty(value = "第三方授权类型")
    private ThirdPartyTypeOfAuth thirdparty_type;

    //第三方openid
    @ApiModelProperty(value = "第三方openid")
    private String thirdparty_openid;

    //用户类型
    @ApiModelProperty(value = "用户类型")
    private UserType type;

    //用户状态
    @ApiModelProperty(value = "用户状态")
    private UserStatus status;

    //用户注册ip
    @ApiModelProperty(value = "用户注册ip")
    private String register_ip;

    //用户注册时间
    @ApiModelProperty(value = "用户注册时间")
    private String create_time;
}
