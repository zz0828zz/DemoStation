package com.demo.station.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.station.model.dto.SysUserDto;
import com.demo.station.model.dto.SysUserInfoDto;
import com.demo.station.model.vo.SaveSysUserVO;
import com.demo.station.model.vo.SelectSysUserPage;
import com.demo.station.model.vo.UpdateSysUserVO;
import com.demo.station.pojo.SysUser;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface SysUserService extends IService<SysUser> {
    boolean saveUser( SaveSysUserVO sysUserVO);
    boolean updateUser( UpdateSysUserVO sysUserVO);

    List<SysUserInfoDto> getSysUserInfoDto(SelectSysUserPage page);
}
