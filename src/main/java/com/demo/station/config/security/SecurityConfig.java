package com.demo.station.config.security;

//import com.demo.station.config.jwt.JwtAuthenticateFilter;
import com.demo.station.config.jwt.JwtAuthenticateFilter;
import com.demo.station.config.jwt.JwtAuthorizationFilter;
//import com.demo.station.config.security.AjaxAuthenticationFailureHandler;
//import com.demo.station.config.security.JwtAccessDeniedHandler;
import com.demo.station.config.security.AjaxAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author lipb
 **/

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;  //  登录失败返回的 JSON 格式数据给前端（否则为 html）
//    @Autowired
//    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
//          .formLogin()  //自定义自己编写的登录页面
//       .loginPage("/login.html")   //登录页面设置
//        .loginProcessingUrl("/api/user/login")   //登录访问路径
//        .defaultSuccessUrl("/api/user/test")  //当登录成功之后，跳转路径
//        .permitAll()
//                .and()
                //授权
                .authorizeRequests()
                //放行
                .antMatchers("/swagger-ui.html/**","/uploadFileServer/**").permitAll()  //设置哪些路径不需要认证   ps：yml中配置的路径不需要写里面
                //当前登录用户，只有具有admins权限才能访问这个路径  hasAuthority（）指定一个权限。.hasAnyAuthority()指定多个权限
                //.antMatchers("/user/test1").hasAuthority("admins")
                //所有的请求都必须认证
                .anyRequest().authenticated()
                .and().httpBasic()
                .and()
                .addFilter(new JwtAuthenticateFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .exceptionHandling()
                .authenticationEntryPoint(ajaxAuthenticationFailureHandler)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();  //关闭csrf防护


    }

    /**
     * 配置无需登陆就可以访问的路径
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //allow Swagger URL to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html", "/doc.html","/webjars/**");
    }

}
