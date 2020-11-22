package com.demo.station.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author lipb
 **/

@Configuration
//@Component
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//          .formLogin()  //自定义自己编写的登录页面
//       .loginPage("/login.html")   //登录页面设置
//        .loginProcessingUrl("/api/user/login")   //登录访问路径
//        .defaultSuccessUrl("/api/user/test")  //当登录成功之后，跳转路径
//        .permitAll()
//                .and()
          .authorizeRequests()
                .antMatchers("/user/userList","/user/test").permitAll()  //设置哪些路径不需要认证   ps：yml中配置的路径不需要写里面
                //当前登录用户，只有具有admins权限才能访问这个路径  hasAuthority（）指定一个权限。.hasAnyAuthority()指定多个权限
                //.antMatchers("/user/test1").hasAuthority("admins")
                .anyRequest().authenticated()
                .and().csrf().disable();  //关闭csrf防护


    }
}
