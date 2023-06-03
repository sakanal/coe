package com.coe.controller.Clazz;

import com.coe.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class ClazzController {
    @Resource
    private ClazzService clazzService;

    @GetMapping("/coe/class/toClassList")
    public ModelAndView toClassList(){
        return new ModelAndView("/ExamTch/Clazz/clazzList");
    }
    @GetMapping("/coe/class/toClassInfo/{id}")
    public ModelAndView toClassInfo(@PathVariable("id")Integer clazzId,
                                    HttpSession httpSession){
        log.info(String.valueOf(clazzId));
        httpSession.setAttribute("clazzId",clazzId);
        return new ModelAndView("/ExamTch/Clazz/clazzInfo");
    }
    @GetMapping("/coe/class/toMyClassList")
    public ModelAndView toMyClassList(){
        return new ModelAndView("/ExamTch/Clazz/MyClazzList");
    }
    @GetMapping("/coe/class/toMyClassInfo/{id}")
    public ModelAndView toMyClassInfo(@PathVariable("id")Integer clazzId,
                                      HttpSession httpSession){
        log.info(String.valueOf(clazzId));
        httpSession.setAttribute("clazzId",clazzId);
        return new ModelAndView("/ExamTch/Clazz/MyClazzInfo");
    }
}
