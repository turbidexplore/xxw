package com.bangxuan.xxw.service;

import com.bangxuan.xxw.dao.UserSecurityMapper;
import com.bangxuan.xxw.entity.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserSecurityService {

    @Autowired
    private UserSecurityMapper userSecurityMapper;

    public UserSecurity findByPhone(String phone){
        return userSecurityMapper.findByPhone(phone);
    }

    public int findCountByPhone(String phone){
        return userSecurityMapper.findCountByPhone(phone);
    }

    @Transactional
    public int add(UserSecurity userSecurity){
        return  userSecurityMapper.insertSelective(userSecurity);
    }

    @Transactional
    public int updateAuthcode(String phone, int authcode) {
        return userSecurityMapper.updateAuthcode(phone,authcode);
    }

    @Transactional
    public int updateByPhoneSelective(UserSecurity userSecurity){
        return  userSecurityMapper.updateByPhoneSelective(userSecurity);
    }

    public UserSecurity findBySMS(String phonenumber, String authcode) {
        return userSecurityMapper.findBySMS(phonenumber,authcode);
    }

    public UserSecurity findAuthCodeByPhone(String username) {
        return userSecurityMapper.findAuthCodeByPhone(username);
    }

    public List<String> findByAdmin() {
        return userSecurityMapper.findByAdmin();
    }

    public Integer findUserdataCount() {
        return userSecurityMapper.findUserdataCount();
    }

    public String findUserCodeByPhone(String name) {
        return userSecurityMapper.findUserCodeByPhone(name);
    }



    @Transactional
    public int changePassword(String name, String password) {
        return userSecurityMapper.changePassword(name,password);
    }
}
