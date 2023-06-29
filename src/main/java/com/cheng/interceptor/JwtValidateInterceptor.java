package com.cheng.interceptor;

import com.alibaba.fastjson2.JSON;
import com.cheng.common.utils.JwtUtil;
import com.cheng.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Aaron
 * @date : 2023/5/6 14:59
 */
@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse  response, @NotNull Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        log.debug(request.getRequestURI() + "需要验证：" + token);
        if (token != null) {
            try {
                jwtUtil.parseToken(token);
                //System.out.println(); //别用sout打印日志信息
                log.debug(request.getRequestURI() + "验证通过");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 判断当前请求的 URL 是否是注册接口，如果是，则直接放行
        if (request.getRequestURI().equals("/user/register") || request.getRequestURI().equals("/doc.html") ) {
            log.debug(request.getRequestURI() + "无需验证，放行");
            return true;
        }
        // 检查请求的 URL 是否是需要放行的静态资源文件
        if (isStaticResource(request.getRequestURI())) {
            log.debug(request.getRequestURI() + "无需验证，放行");
            return true;
        }

        log.debug(request.getRequestURI() + "验证失败，禁止访问");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.fail(20003,"jwt令牌无效，请重新登录")));
         return false; // 拦截
    }

    // 判断请求的 URL 是否是需要放行的静态资源文件
    private boolean isStaticResource(String requestUri) {
        return requestUri.startsWith("/webjars/css/") || requestUri.startsWith("/webjars/js/") || requestUri.startsWith("/favicon.ico");
    }
}
