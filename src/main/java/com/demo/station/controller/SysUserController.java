package com.demo.station.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.station.utils.CopyUtils;
import com.demo.station.utils.PageResult;
import com.demo.station.utils.Result;
import com.demo.station.model.dto.SysUserDto;
import com.demo.station.pojo.SysUser;
import com.demo.station.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lipb
 **/
@RestController
@RequestMapping("/user")
@Api(tags = "工作流任务管理")
public class SysUserController {
    @Autowired
    private SysUserService userService;

    @GetMapping(value = "/getUserList")
    @ApiOperation("获取所有用户，分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",required = true),
            @ApiImplicitParam(name = "size", value = "每页数量",required = true)
    })
    public PageResult getUserList(Integer current , Integer size){
        Page<SysUser> page = new Page<>(current, size);  //参数一是当前页，参数二是每页个数

        userService.page(page,null);

        return CopyUtils.copyPage(page, SysUserDto.class);
    }

    @GetMapping(value = "/getUserById")
    @ApiOperation("根据用户id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户id",required = true)
    public Result getUserById(Integer id){
        SysUser user = userService.getById(id);
        SysUserDto userDto = CopyUtils.copyPojo(user, SysUserDto.class);
        return Result.data(userDto);
    }

    @PostMapping(value = "/saveUser")
    @ApiOperation("新增用户")
    public Result saveUser(@RequestBody SysUser user) {
        if (user != null) {
            user.setUserPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));
            if (userService.save(user)) {
                return Result.success("新增成功");
            } else {
                return Result.fail("新增失败");
            }

        } else {
            return Result.fail("用户为空！");
        }
    }

    @PostMapping(value = "/delUser")
    @ApiOperation("根据用户id删除用户")
    @ApiImplicitParam(name = "id", value = "用户id",required = true)
    public Result delUser(Integer id){
        if (userService.removeById(id)) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }




}
