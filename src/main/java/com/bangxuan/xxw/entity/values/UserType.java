package com.bangxuan.xxw.entity.values;

/**
 * 用户类型
 */
public enum UserType {

    SeniorAdministrator(0),//高级管理员
    GeneralAdministrator(1),//普通管理员
    GeneralPersonal(2),//个人用户
    AdvancedPersonal(3),//高级个人用户
    GeneralCompany(4),//企业用户
    AdvancedCompany(5),//高级企业用户
    test(6);//游客
    UserType(int value) {

    }
}
