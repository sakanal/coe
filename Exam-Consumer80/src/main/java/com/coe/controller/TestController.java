package com.coe.controller;

import com.coe.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class TestController {
    @Resource
    private TestService testService;

    @RequestMapping("/TestConsumer/testSession")
    public ModelAndView testSession(HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView("test");
//        httpSession.setAttribute("userName","root");
//        testService.testSession();
//        String userPassword = (String) httpSession.getAttribute("userPassword");
//        log.info("userPassword-->"+userPassword);
        return modelAndView;
    }
    @RequestMapping("/ExamTch/lose.html")
    public ModelAndView toLost(){
        return new ModelAndView("/ExamTch/lose");
    }
    @RequestMapping("/ExamTch/success.html")
    public ModelAndView toSuccess(){
        return new ModelAndView("/ExamTch/success");
    }


}
