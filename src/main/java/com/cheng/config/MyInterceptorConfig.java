package com.cheng.config;

import com.cheng.interceptor.JwtValidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author : Aaron
 * @date : 2023/5/6 15:21
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Resource
    private JwtValidateInterceptor jwtValidateInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor = registry.addInterceptor(jwtValidateInterceptor);
        interceptor.addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",
                        "/user/info",
                        "/user/logout",
                        "/error",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/**"
                );

    }
}
