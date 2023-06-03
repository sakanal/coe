package com.coe.controller.User;

import com.coe.entities.CommonResult;
import com.coe.entities.Student;
import com.coe.entities.Teacher;
import com.coe.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class UserController {
    @Resource
    private StudentService studentService;

    /**登录页*/
    @GetMapping("/login")
    public ModelAndView getView1(){
        return new ModelAndView("/User/login");
    }

    /**注册页*/
    @GetMapping("/register")
    public ModelAndView getView2(){
        return new ModelAndView("/User/register");
    }

    @GetMapping("/logout")
    public Boolean logout(HttpSession session){
        session.invalidate();
        return true;
    }

    @GetMapping("/getUserId")
    public Long getUserId(HttpSession httpSession){
        log.info("获取用户Id中...");
        return (Long) httpSession.getAttribute("id");
    }
    @GetMapping("/getUserIdentity")
    public Integer getUserIdentity(HttpSession session){
        log.info("获取用户身份中...");
        return (Integer) session.getAttribute("identity");
    }
    @GetMapping("/getUserName")
    public String getUserName(HttpSession session){
        Integer identity = (Integer) session.getAttribute("identity");
        Long id = (Long) session.getAttribute("id");
        String name=null;
        if (identity==1){
            CommonResult<Student> byStudentId = studentService.getByStudentId(id);
            if (byStudentId.getCode()==200){
                name=byStudentId.getData().getName();
            }
        }else if (identity==2){
            CommonResult<Teacher> byTeacherId = studentService.getByTeacherId(id);
            if (byTeacherId.getCode()==200){
                name=byTeacherId.getData().getName();
            }
        }
        return name;
    }
}
