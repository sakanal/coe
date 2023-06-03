package com.coe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@RestController
@Slf4j
public class TestController {
    @RequestMapping("/testSession")
    public void testSession(HttpServletRequest httpServletRequest){
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        if (headerNames!=null){
            while (headerNames.hasMoreElements()){
                String name = headerNames.nextElement();
                String header = httpServletRequest.getHeader(name);
                log.info("name-->"+name);
                log.info("header-->"+header);
            }
        }
        String userName = httpServletRequest.getHeader("userName");
        log.info("userName-->"+userName);
        httpServletRequest.setAttribute("userPassword","123456");
//        HttpSession httpSession = httpServletRequest.getSession();
//        System.out.println(httpServletRequest);
//        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
//        log.info("headerNames-->"+headerNames);
//        String userNameString = httpServletRequest.getHeader("userName");
//        log.info("userNameString-->"+userNameString);
//        String userName = (String) httpSession.getAttribute("userName");
//        log.info(userName);
    }
}
