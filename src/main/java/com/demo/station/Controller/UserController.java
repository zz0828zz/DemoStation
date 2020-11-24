package com.demo.station.Controller;

import com.demo.station.Utils.Result;
import com.demo.station.pojo.User;
import com.demo.station.service.UserService;
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
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/saveUser")
    public Result test1(@RequestBody User user){
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
