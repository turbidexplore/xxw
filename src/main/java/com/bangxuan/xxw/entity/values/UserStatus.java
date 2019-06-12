package com.bangxuan.xxw.entity.values;

/**
 * 用户状态
 */
public enum UserStatus {

    Normal(0),//用户状态正常
    Abnormal(1),//用户状态异常
    Checking(2),//用户状态检查中
    auth(3)//用户状态身份已认证
    ;
    UserStatus(int value) {
    }
}
