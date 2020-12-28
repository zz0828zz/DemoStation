//package com.demo.station.config.security;
//
//import com.demo.station.utils.Result;
//import net.sf.json.JSONObject;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//
///**
// * @author : lipb
// * @date : 2020-12-28 15:43
// */
//@Component
//public class JwtAccessDeniedHandler implements AccessDeniedHandler {
//    @Override
//    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException exception) throws IOException, ServletException {
//        httpServletResponse.setContentType("application/json; charset=utf-8");
//        Result<Map<String, String>> data = null;
//        data = Result.fail(exception.getMessage());
//
//
////        if (exception instanceof UsernameNotFoundException) {
////            data = Result.fail("用户不存在!");
////        } else if (exception instanceof BadCredentialsException) {
////            data = Result.fail("用户名或密码错误！");
////        } else if (exception instanceof LockedException) {
////            data = Result.fail("用户已被锁定！");
////        } else if (exception instanceof DisabledException) {
////            data = Result.fail("用户不可用！");
////        } else if (exception instanceof AccountExpiredException) {
////            data = Result.fail("账户已过期！");
////        } else if (exception instanceof CredentialsExpiredException) {
////            data = Result.fail("用户密码已过期！");
////        } else {
////            data = Result.fail("认证失败，请联系网站管理员！");
////        }
//
//        JSONObject jsonObject = JSONObject.fromObject(data);
//        httpServletResponse.getWriter().write(jsonObject.toString());
//
//    }
//}
