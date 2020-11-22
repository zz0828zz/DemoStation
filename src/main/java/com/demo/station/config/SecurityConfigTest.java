package com.demo.station.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author lipb
 **/

//@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //创建密码解析器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //对密码进行加密
        String password = passwordEncoder.encode("zsw");
        //打印加密之后的数据

        //判断原字符串加密后和加密之前是否匹配


        auth.inMemoryAuthentication().withUser("zsw").password(password).roles("");
    }

    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }
}
