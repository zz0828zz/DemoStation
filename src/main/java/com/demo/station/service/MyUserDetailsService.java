package com.demo.station.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.station.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        //调用 userMapper 根据用户名   方法查询数据库
        QueryWrapper<com.demo.station.pojo.User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        com.demo.station.pojo.User user = userMapper.selectOne(queryWrapper);
        //判断
        if (user == null){  //数据库中没有用户名，认证失败
            throw new UsernameNotFoundException("用户名不存在！");
        }
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins");
        return new User(user.getUserName(),new BCryptPasswordEncoder().encode(user.getUserPassword()),auths);
    }
}
