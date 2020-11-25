package com.demo.station.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.station.model.dto.SysUserDto;
import com.demo.station.pojo.SysUser;

public interface SysUserService extends IService<SysUser> {
    boolean saveUser(SysUserDto userDto);
}
