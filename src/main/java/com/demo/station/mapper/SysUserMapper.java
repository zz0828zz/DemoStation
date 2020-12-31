package com.demo.station.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.station.model.dto.SysUserInfoDto;
import com.demo.station.model.vo.SelectSysUserPage;
import com.demo.station.pojo.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUserInfoDto> getSysUserInfoDto(@Param("page") SelectSysUserPage page);
}
