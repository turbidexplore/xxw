package com.bangxuan.xxw.entity;

import com.bangxuan.xxw.entity.values.ThirdPartyTypeOfAuth;
import com.bangxuan.xxw.entity.values.UserStatus;
import com.bangxuan.xxw.entity.values.UserType;

import java.util.UUID;

public class UserSecurity {

    public UserSecurity(){
        if(this.id==null) {
            this.id = UUID.randomUUID().toString().replace("-", "");
            this.user_code = UUID.randomUUID().toString().replace("-", "");
        }
    }

    private String id;

    private String user_code;

    private String password;

    private String saltvalue;

    private String phonenumber;

    private String email;

    private String authcode;

    private String sendauthcode_time;

    private ThirdPartyTypeOfAuth thirdparty_type;

    private String thirdparty_openid;

    private UserType type;

    private UserStatus status;

    private String register_ip;

    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSaltvalue() {
        return saltvalue;
    }

    public void setSaltvalue(String saltvalue) {
        this.saltvalue = saltvalue;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public String getSendauthcode_time() {
        return sendauthcode_time;
    }

    public void setSendauthcode_time(String sendauthcode_time) {
        this.sendauthcode_time = sendauthcode_time;
    }

    public ThirdPartyTypeOfAuth getThirdparty_type() {
        return thirdparty_type;
    }

    public void setThirdparty_type(ThirdPartyTypeOfAuth thirdparty_type) {
        this.thirdparty_type = thirdparty_type;
    }

    public String getThirdparty_openid() {
        return thirdparty_openid;
    }

    public void setThirdparty_openid(String thirdparty_openid) {
        this.thirdparty_openid = thirdparty_openid;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getRegister_ip() {
        return register_ip;
    }

    public void setRegister_ip(String register_ip) {
        this.register_ip = register_ip;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
