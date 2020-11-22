package com.demo.station.Controller;

import com.demo.station.Utils.Result;
import com.demo.station.pojo.User;
import com.demo.station.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author lipb
 **/
@RestController
@RequestMapping("user")
public class LoginController {
    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping(value = "/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUserName();
        username = HtmlUtils.htmlEscape(username);

        if (!Objects.equals("admin", username) || !Objects.equals("123456", requestUser.getUserPassword())) {
            String message = "账号密码错误";
            System.out.println("test");
            return new Result(400);
        } else {
            return new Result(200);
        }
    }

    @GetMapping(value = "/userList")
    public void userList(){
        List<User> list = userService.list();
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
