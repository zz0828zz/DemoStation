package com.demo.station.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.station.mapper.SysRoleMapper;
import com.demo.station.mapper.SysUserMapper;
import com.demo.station.mapper.SysUserRoleMapper;
import com.demo.station.pojo.SysRole;
import com.demo.station.pojo.SysUser;
import com.demo.station.pojo.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        //调用 userMapper 根据用户名   方法查询数据库
        QueryWrapper<SysUser> queryWrapperUser = new QueryWrapper<>();
        queryWrapperUser.eq("user_name",userName);
        SysUser user = userMapper.selectOne(queryWrapperUser);
        //判断
        if (user == null){  //数据库中没有用户名，认证失败
            throw new UsernameNotFoundException("用户名"+userName+"不存在！");
        }else{
            List<GrantedAuthority> auths = new ArrayList<>();
            QueryWrapper<SysUserRole> queryWrapperUserRole = new QueryWrapper<>();
            queryWrapperUserRole.eq("user_id",user.getId());
            List<Long> roleIdList = userRoleMapper.selectList(queryWrapperUserRole).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(roleIdList)){
                List<SysRole> roles = roleMapper.selectBatchIds(roleIdList);
                String collect = roles.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
                auths = AuthorityUtils.commaSeparatedStringToAuthorityList(collect);
            }
            return new User(user.getUserName(),user.getUserPassword(),auths);
        }

    }
}
