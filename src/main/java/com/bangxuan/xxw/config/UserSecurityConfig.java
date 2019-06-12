package com.bangxuan.xxw.config;

import com.bangxuan.xxw.entity.values.UserType;
import com.bangxuan.xxw.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable().formLogin().loginPage("/system/login").permitAll().successForwardUrl("/system/index")
                .and().authorizeRequests().antMatchers("/system/login").permitAll()
                .antMatchers("/system/**").authenticated() .antMatchers("/file/pdf").authenticated()      // 任何请求,登录后可以访问
                .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //全局替换UserDetailsService
        auth.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    @Primary
    public UserDetailsService userDetailsService(){
        return customerUserDetailsService;
    }
}
