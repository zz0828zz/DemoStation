package com.demo.station.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.station.mapper.SysUserMapper;
import com.demo.station.model.dto.SysUserDto;
import com.demo.station.pojo.SysUser;
import com.demo.station.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author lipb
 **/
@Service
public class UserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper userMapper;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public boolean saveUser(SysUserDto userDto) {
        SysUser user = new SysUser();
        user.setUserName(userDto.getUserName());
//        user.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return true;
        } else {
            return false;
        }

    }
}
