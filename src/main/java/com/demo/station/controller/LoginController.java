//package com.demo.station.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.demo.station.mapper.SysUserMapper;
//import com.demo.station.model.dto.AuthInfo;
//import com.demo.station.pojo.SysUser;
//import com.demo.station.utils.SecurityConstants;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @Author lipb
// **/
//@RestController
//@RequestMapping("/login")
//public class LoginController {
//    private static final String strKey = "1YGZJCiSuFkyw4luzkNma4VqRf4ULYMpKkQTu8CE9iD4=";
//    @Autowired
//    private UserDetailsService userDetailsService;
//    @Autowired
//    private SysUserMapper userMapper;
//
//    @GetMapping(value = "/getAuthInfo")
//    public AuthInfo getAuthInfo(String userName){
//        UserDetails user = userDetailsService.loadUserByUsername(userName);
//        List<String> roles = user.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//        SecretKey key = Keys.hmacShaKeyFor(strKey.getBytes());
//        String token = Jwts.builder()
//                .setHeaderParam("TYP", SecurityConstants.TOKEN_TYPE)
//                .setIssuer(SecurityConstants.TOKEN_ISSUER)
//                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
//                .setExpiration(new Date(System.currentTimeMillis() + 100000))   //失效时间
//                .setSubject(user.getUsername())
//                .setIssuedAt(new Date())
//                .claim("rol",roles)
//                .signWith(key).compact();
//
//
//
//        QueryWrapper<SysUser> queryWrapperUser = new QueryWrapper<>();
//        queryWrapperUser.eq("user_name",userName);
//        SysUser sysUser = userMapper.selectOne(queryWrapperUser);
//
//        AuthInfo authInfo = new AuthInfo();
//        authInfo.setUserId(sysUser.getId());
//        authInfo.setAccessToken(SecurityConstants.TOKEN_PREFIX+token);
//
//        return authInfo;
//    }
//
//}
