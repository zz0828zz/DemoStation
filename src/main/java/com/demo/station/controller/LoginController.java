package com.demo.station.controller;

import com.demo.station.Utils.Result;
import com.demo.station.pojo.SysUser;
import com.demo.station.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author lipb
 **/
@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private SysUserService userService;


//    @CrossOrigin
//    @PostMapping(value = "/login")
//    @ResponseBody
//    public Result login() {
//
//    }

    @GetMapping(value = "/userList")
    public void userList(){
        List<SysUser> list = userService.list();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @GetMapping(value = "/test")
    public String test(){
       return "hello,zsw";
    }

    @GetMapping(value = "/test1")
    public String test1(){
        return "hello,test1";
    }
}
