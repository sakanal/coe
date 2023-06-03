package com.coe.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开始运行拦截器...");
        HttpSession session = request.getSession();
        Long id =(Long) session.getAttribute("id");
        log.info(String.valueOf(id));
        if (id!=null && id!=0){
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }else {
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }


    }
}
