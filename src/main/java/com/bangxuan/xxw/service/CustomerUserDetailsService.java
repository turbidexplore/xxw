package com.bangxuan.xxw.service;

import com.bangxuan.xxw.entity.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest servletRequest =  requestAttributes.getRequest();
        String login_type= servletRequest.getParameter("login_type").trim();
//        if(login_type!="sms"&&login_type!="password"&&!login_type.equals("sms")&&!login_type.equals("password")){
//            return  new org.springframework.security.core.userdetails.User(username,userService.selectPasswordByOpenid(username,login_type),true,true,true,true,AuthorityUtils.NO_AUTHORITIES);
//        }
        UserSecurity userSecurity = userSecurityService.findByPhone(username);
        String password="";
        if (userSecurity==null){
            throw new UsernameNotFoundException("用户不存在");
        }else {
            if (login_type.equals("sms") || login_type == "sms") {
                userSecurity = userSecurityService.findAuthCodeByPhone(username);
                if (userSecurity == null) {
                    throw new UsernameNotFoundException("验证码无效");
                }else {
                    password =bCryptPasswordEncoder.encode(userSecurity.getAuthcode());
                }

            } else  {
                password = userSecurity.getPassword();
            }
        }
        return  new org.springframework.security.core.userdetails.User(username,password,true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList(userSecurity.getType().toString()));
    }
}
