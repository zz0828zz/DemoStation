package com.demo.station.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.station.config.GoalException.BusinessException;
import com.demo.station.config.UserUtils;
import com.demo.station.model.vo.PageInfo;
import com.demo.station.pojo.SysUserRole;
import com.demo.station.service.SysRoleService;
import com.demo.station.service.SysUserRoleService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lipb
 **/
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class SysUserController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @GetMapping(value = "/getUser")
    @ApiOperation("获取当前登录用户")
    public Result getUser(){

        String s = UserUtils.getUser();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",s);
        SysUser sysUser = userService.getOne(queryWrapper);
        return Result.data(sysUser);
    }

    @GetMapping(value = "/getUserPage")
    @ApiOperation("获取所有用户，分页")

    public PageResult getUserPage(@RequestBody PageInfo pageInfo){
        Page<SysUser> page = new Page<>(pageInfo.getCurrent(), pageInfo.getSize());  //参数一是当前页，参数二是每页个数

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

    @PostMapping(value = "/updateUser")
    @ApiOperation("更新用户")
    public Result updateUser(@RequestBody SysUser user) {
        if (user != null) {
            if (user.getUserPassword()!=null){
                user.setUserPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));
            }
            if (userService.save(user)) {
                return Result.success("更新成功");
            } else {
                return Result.fail("更新失败");
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

    @PostMapping(value = "/addRoleByUser")
    @ApiOperation("给用户添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id",required = true),
            @ApiImplicitParam(name = "roleIds", value = "角色id集合",required = true)
    })

    public Result addRoleByUser(Long userId,String roleIds){
        String[] roleIdArr = roleIds.split(",");
        if (roleIdArr != null && roleIdArr.length > 0) {
            List<SysUserRole> list = new ArrayList<>();
            SysUserRole sysUserRole;
            for (String roleId : roleIdArr) {
                sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(Long.getLong(roleId));
                list.add(sysUserRole);
            }
            if (sysUserRoleService.saveBatch(list)) {
                return Result.success("添加成功");
            }else {
                return Result.fail("添加失败");
            }
        } else {
            return Result.fail("请选择角色");
        }


    }

}
