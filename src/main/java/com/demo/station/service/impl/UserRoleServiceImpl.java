package com.demo.station.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.station.mapper.UserRoleMapper;
import com.demo.station.pojo.UserRole;
import com.demo.station.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author : lipb
 * @date : 2020-11-23 15:13
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
