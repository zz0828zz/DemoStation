package com.demo.station.config.security;

import com.demo.station.utils.Result;
import com.demo.station.utils.ResultCode;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author : lipb
 * @date : 2020-12-28 10:49
 */
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json; charset=utf-8");
        Result<Map<String, String>> data = null;



        if (exception instanceof UsernameNotFoundException) {
            data = Result.fail(ResultCode.UN_AUTHORIZED.getCode(),"用户不存在!");
        } else if (exception instanceof BadCredentialsException) {
            data = Result.fail(ResultCode.UN_AUTHORIZED.getCode(),"用户名或密码错误！");
        } else if (exception instanceof LockedException) {
            data = Result.fail(ResultCode.UN_AUTHORIZED.getCode(),"用户已被锁定！");
        } else if (exception instanceof DisabledException) {
            data = Result.fail(ResultCode.UN_AUTHORIZED.getCode(),"用户不可用！");
        } else if (exception instanceof AccountExpiredException) {
            data = Result.fail(ResultCode.UN_AUTHORIZED.getCode(),"账户已过期！");
        } else if (exception instanceof CredentialsExpiredException) {
            data = Result.fail(ResultCode.UN_AUTHORIZED.getCode(),"用户密码已过期！");
        } else {
            data = Result.fail(ResultCode.UN_AUTHORIZED.getCode(),"用户名或密码错误！");
        }

        JSONObject jsonObject = JSONObject.fromObject(data);
        httpServletResponse.getWriter().write(jsonObject.toString());
    }

}
