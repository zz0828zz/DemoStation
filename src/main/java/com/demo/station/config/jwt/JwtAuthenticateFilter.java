package com.demo.station.config.jwt;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.station.mapper.SysRoleMapper;
import com.demo.station.mapper.SysUserMapper;
import com.demo.station.mapper.SysUserRoleMapper;
import com.demo.station.pojo.SecurityConstants;
import com.demo.station.pojo.SysRole;
import com.demo.station.pojo.SysUser;
import com.demo.station.pojo.SysUserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : lipb
 * @date : 2020-11-24 15:51
 */
@Log4j2
public class JwtAuthenticateFilter extends UsernamePasswordAuthenticationFilter {
    private static final String strKey = "1YGZJCiSuFkyw4luzkNma4VqRf4ULYMpKkQTu8CE9iD4=";
    private final AuthenticationManager authenticationManager;


    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    public JwtAuthenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
       // setFilterProcessesUrl("/token");  //获取token的接口
    }


    /*
     *  获取请求信息
      */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String userName = request.getParameter("userName");
//        String userPassword = request.getParameter("userPassword");
        SysUser sysUser = paraData(request);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserName(),sysUser.getUserPassword());
       return this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);


    }

    /*
    *  返回token
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        SecretKey key = Keys.hmacShaKeyFor(strKey.getBytes());
        String token = Jwts.builder()
                .setHeaderParam("TYP", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setExpiration(new Date(System.currentTimeMillis() + 100000))   //失效时间
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .claim("rol",roles)
                .signWith(key).compact();

        response.setHeader("Authorization","Bearer "+token);

    }

    private SysUser paraData(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(request.getInputStream(),SysUser.class);
    }
}
