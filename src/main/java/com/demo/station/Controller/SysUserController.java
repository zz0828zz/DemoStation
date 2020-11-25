package com.demo.station.Controller;

import com.demo.station.Utils.Result;
import com.demo.station.pojo.SysUser;
import com.demo.station.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author lipb
 **/
@RestController
@RequestMapping("user")
public class SysUserController {
    @Autowired
    private SysUserService userService;

    @PostMapping(value = "/saveUser")
    public Result test1(@RequestBody SysUser user){
    if (user!=null){
        user.setUserPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));
        if (userService.save(user)) {
            return Result.success("新增成功");
        }else{
            return Result.fail("新增失败");
        }

    }else{
        return Result.fail("用户为空！");
    }
    }
}
