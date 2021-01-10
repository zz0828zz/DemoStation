package com.demo.station.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.station.config.jwt.UserUtils;
import com.demo.station.model.dto.ProductDto;
import com.demo.station.model.dto.SysUserInfoDto;
import com.demo.station.model.vo.SaveSysUserVO;
import com.demo.station.model.vo.SelectSysUserPage;
import com.demo.station.model.vo.UpdateSysUserVO;
import com.demo.station.pojo.*;
import com.demo.station.service.ProductPriceService;
import com.demo.station.service.SysRoleService;
import com.demo.station.service.SysUserRoleService;
import com.demo.station.utils.CopyUtils;
import com.demo.station.utils.PageResult;
import com.demo.station.utils.Result;
import com.demo.station.model.dto.SysUserDto;
import com.demo.station.service.SysUserService;
import com.demo.station.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
    @Autowired
    private ProductPriceService productPriceService;

    @GetMapping(value = "/getUser")
    @ApiOperation("获取当前登录用户信息")
    public Result<SysUserDto> getUser() {

        String s = UserUtils.getUser();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", s);
        SysUser sysUser = userService.getOne(queryWrapper);
        SysUserDto sysUserDto = null;

        if (sysUser != null) {

            sysUserDto = new SysUserDto();
            sysUserDto.setUserId(sysUser.getId());
            sysUserDto.setUserName(sysUser.getUserName());
            sysUserDto.setPhone(sysUser.getPhone());
            sysUserDto.setQq(sysUser.getQq());
            List<String> roles = new ArrayList<>();
            //查询用户角色
            QueryWrapper<SysUserRole> sysUserRoleQuery = new QueryWrapper<>();
            sysUserRoleQuery.eq("user_id", sysUser.getId());
            List<SysUserRole> sysUserRoleList = sysUserRoleService.list(sysUserRoleQuery);
            if (!CollectionUtils.isEmpty(sysUserRoleList)) {
                for (SysUserRole sysUserRole : sysUserRoleList) {
                    SysRole sysRole = sysRoleService.getById(sysUserRole.getRoleId());
                    roles.add(sysRole.getRoleName());
                }
                sysUserDto.setRoles(roles);
                return Result.data(sysUserDto);
            } else {
                return Result.fail("该用户缺少角色");
            }

        } else {
            return Result.fail(ResultCode.UN_AUTHORIZED.getCode(), "未查到用户信息！");
        }

    }


    @GetMapping(value = "/getSysUserInfoDto")
    @ApiOperation("获取所有下属用户，分页")
    public PageResult<SysUserInfoDto> getSysUserInfoDto(SelectSysUserPage page) {
        page.setFirstIndex((page.getCurrent() - 1) * page.getSize());
        String sysUserName = page.getSysUserName();

        if (sysUserName == null) {
            sysUserName = UserUtils.getUser(); //获取当前用户账号
        }

        page.setSysUserName(sysUserName);
        List<SysUserInfoDto> sysUserInfoDtoList = userService.getSysUserInfoDto(page);
        PageResult pageResult = new PageResult();
        if (CollectionUtils.isEmpty(sysUserInfoDtoList)) {
            return pageResult;
        }

        int count = userService.getSysUserCount(page);
        //封装page类
        pageResult.setCount(new Long(count));
        pageResult.setData(sysUserInfoDtoList);

        return pageResult;
    }

    @GetMapping(value = "/getUserById")
    @ApiOperation("根据用户id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true)
    public Result getUserById(Integer id) {
        SysUser user = userService.getById(id);
        SysUserDto userDto = CopyUtils.copyPojo(user, SysUserDto.class);
        return Result.data(userDto);
    }

    @PostMapping(value = "/saveUser")
    @ApiOperation("新增用户")
    public Result saveUser(@RequestBody SaveSysUserVO sysUserVO) {
        String sysUserName = UserUtils.getUser(); //获取当前用户账号
        sysUserVO.setAgentName(sysUserName);
        if (userService.saveUser(sysUserVO)) {
            return Result.success("新增成功");
        } else {
            return Result.fail("新增失败");
        }

    }

    @PostMapping(value = "/updateUser")
    @ApiOperation("更新用户")
    public Result updateUser(@RequestBody UpdateSysUserVO sysUserVO) {

        if (userService.updateUser(sysUserVO)) {
            return Result.success("更新成功");
        } else {
            return Result.fail("更新失败");
        }
    }


    @PostMapping(value = "/delUser")
    @ApiOperation("根据用户id删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", required = true)
    @Transactional
    public Result delUser(Long id) {
        SysUser sysUser = userService.getById(id);
        QueryWrapper<ProductPrice> productPriceQuery = new QueryWrapper<>();
        productPriceQuery.eq("user_name", sysUser.getUserName());
        productPriceService.remove(productPriceQuery);
        if (userService.removeById(id)) {
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", id);
            sysUserRoleService.remove(queryWrapper);
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }


    @GetMapping(value = "/getQqByUserId")
    @ApiOperation("根据用户id获取代理qq号")
    @ApiImplicitParam(name = "id", value = "用户id", required = true)
    public Result getQqByUserId(Long id) {
        //先获取当前用户信息
        SysUser sysUser = userService.getById(id);
        if (!StringUtils.isEmpty(sysUser.getAgentName())) {
            //再根据获取到的用户信息  找到代理的账号
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", sysUser.getAgentName());
            SysUser userServiceOne = userService.getOne(queryWrapper);

            //最后将代理的qq返回给首页
            return Result.data(userServiceOne.getQq());
        }
        return Result.data(null);
    }




}
