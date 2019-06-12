package com.bangxuan.xxw.entity.values;

/**
 * 用户登录类型
 */
public enum  LoginType {
    Password(0),//密码模式
    AuthCode(1),//验证码（授权码）模式
    ThirdParty(2);//第三方授权模式

    LoginType(int value) {
    }
}
