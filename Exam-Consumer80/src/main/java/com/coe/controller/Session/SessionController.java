package com.coe.controller.Session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/Session")
@Slf4j
public class SessionController {
    @GetMapping("/getClazzId")
    public Integer getClazzId(HttpSession session){
//        log.info("获取到的班级id"+ session.getAttribute("clazzId"));
        return (Integer) session.getAttribute("clazzId");
    }
    @GetMapping("/getExamId")
    public Integer getExamId(HttpSession session){
//        log.info("获取到的试卷id"+session.getAttribute("examId"));
        return (Integer) session.getAttribute("examId");
    }
}
