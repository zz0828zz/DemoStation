package com.demo.station.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.station.model.dto.SysUserDto;
import com.demo.station.pojo.SysRole;
import com.demo.station.pojo.SysUser;
import com.demo.station.service.SysRoleService;
import com.demo.station.service.SysUserService;
import com.demo.station.utils.CopyUtils;
import com.demo.station.utils.PageResult;
import com.demo.station.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lipb
 **/
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class SysRoleController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping(value = "/getRoleList")
    @ApiOperation("获取全部角色")
    public Result getRoleList() {
        return Result.data(sysRoleService.list());
    }



}
