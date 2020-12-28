package com.demo.station.config.jwt;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.demo.station.config.GoalException.BusinessException;
import com.demo.station.utils.Result;
import com.demo.station.utils.ResultCode;
import com.demo.station.utils.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : lipb
 * @date : 2020-11-25 13:19
 */
@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //从request中获取 header
        String token = request.getHeader(SecurityConstants.TOKEN_HEADER);
        //对header进行判断
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                //对token进行校验
                Jws<Claims> parsedToken = Jwts.parserBuilder()
                        .setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
                        .build()
                        .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""));
                //token中获取username
                String username = parsedToken.getBody().getSubject();
                //token中获取权限
                List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
                        .get("rol")).stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());
                if (StringUtils.isNotEmpty(username)) {
                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                }

            } catch (ExpiredJwtException e) {
                //请求过期的JWT
                log.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
                throw new BusinessException("token过期", ResultCode.UN_AUTHORIZED.getCode());

            } catch (UnsupportedJwtException e){
                //请求解析不支持的JWT
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
                throw new BusinessException("请求解析不支持的JWT", ResultCode.UN_AUTHORIZED.getCode());
            } catch (MalformedJwtException e){
                //请求解析无效的JWT
                log.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
                throw new BusinessException("请求解析无效的JWT", ResultCode.UN_AUTHORIZED.getCode());
            } catch (SignatureException e){
                //请求解析带有无效签名的JWT
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, e.getMessage());
                throw new BusinessException("请求解析带有无效签名的JWT", ResultCode.UN_AUTHORIZED.getCode());
            } catch (IllegalArgumentException e){
                //请求解析空或JWT为空
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
                throw new BusinessException("请求解析空或JWT为空", ResultCode.UN_AUTHORIZED.getCode());
            }

        }
        return null;
    }
}
