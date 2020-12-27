package com.demo.station.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.station.pojo.SysUser;
import com.demo.station.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author lipb
 **/
@Component
public class UserUtils {


    public static String getUser() {
        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return principal;
    }
}
