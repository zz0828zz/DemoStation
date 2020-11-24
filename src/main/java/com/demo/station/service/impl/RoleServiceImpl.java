package com.demo.station.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.station.mapper.RoleMapper;
import com.demo.station.pojo.Role;
import com.demo.station.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author : lipb
 * @date : 2020-11-23 15:05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
