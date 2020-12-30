package com.demo.station.config.jwt;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.station.pojo.SysUser;
import com.demo.station.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 获取但当前登录用户
 * @Author lipb
 **/
@Component
public class UserUtils {

    //获取当前登录用户
    public static String getUser() {
        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return principal;
    }
}
