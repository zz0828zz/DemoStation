package com.demo.station.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.station.mapper.RoleMapper;
import com.demo.station.mapper.UserMapper;
import com.demo.station.mapper.UserRoleMapper;
import com.demo.station.pojo.Role;
import com.demo.station.pojo.UserRole;
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
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        //调用 userMapper 根据用户名   方法查询数据库
        QueryWrapper<com.demo.station.pojo.User> queryWrapperUser = new QueryWrapper<>();
        queryWrapperUser.eq("user_name",userName);
        com.demo.station.pojo.User user = userMapper.selectOne(queryWrapperUser);
        //判断
        if (user == null){  //数据库中没有用户名，认证失败
            throw new UsernameNotFoundException("用户名"+userName+"不存在！");
        }else{
            List<GrantedAuthority> auths = new ArrayList<>();
            QueryWrapper<UserRole> queryWrapperUserRole = new QueryWrapper<>();
            queryWrapperUserRole.eq("user_id",user.getId());
            List<UserRole> userRoleList = userRoleMapper.selectList(queryWrapperUserRole);

            if (!CollectionUtils.isEmpty(userRoleList)){
                List<Role> roles = roleMapper.selectBatchIds(userRoleList);
                String collect = roles.stream().map(Role::getRoleName).collect(Collectors.joining(","));
                auths = AuthorityUtils.commaSeparatedStringToAuthorityList(collect);
            }
            return new User(user.getUserName(),new BCryptPasswordEncoder().encode(user.getUserPassword()),auths);
        }

    }
}
