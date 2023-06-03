package com.coe.controller.User;

import com.coe.entities.CommonResult;
import com.coe.entities.Student;
import com.coe.entities.Teacher;
import com.coe.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class StudentController {
    @Resource
    private StudentService studentService;

    @GetMapping("/toStudentIndex")
    public ModelAndView toStudentIndex(){
        return new ModelAndView("/ExamStu/index");
    }
    /**学生个人中心页*/
    @GetMapping("/students")
    public ModelAndView getView(){
        return new ModelAndView("/ExamStu/Student/student");
    }

    /**学生注册*/
    @PostMapping("/students/save")
    public CommonResult save(@RequestBody Student student,HttpSession session){
        Long id = student.getId();
        log.info(String.valueOf(student.getId()));
        if (id>0){
            String password = student.getPassword();
            if (password!=null && !"".equals(password)){
                String name = student.getName();
                if (name!=null && !"".equals(name)){
                    session.setAttribute("id",student.getId());
                    session.setAttribute("identity",1);
                    return studentService.save(student);
                }else {
                    return new CommonResult(444,"未输学生姓名，请重新输入");
                }
            }else {
                return new CommonResult(444,"未输入学生密码，请重新输入");
            }
        }else {
            return new CommonResult(444,"未输入学生编号，请重新输入");
        }
    }

    /**学生修改个人信息*/
    @PutMapping("/students/update")
    public CommonResult update(@RequestBody Student student){
        return studentService.update(student);
    }

    /**学生查询个人信息*/
    @GetMapping("/students/getById/{id}")
    public CommonResult getById(@PathVariable("id") Long id){
        return studentService.getByStudentId(id);
    }

    /**学生登录验证*/
    @PostMapping("/students/login")
    public boolean login(@RequestBody Student student, HttpSession session){
        System.out.println(student);
        Boolean login = studentService.login(student);
        if (login){
            session.setAttribute("id",student.getId());
            session.setAttribute("identity",1);
        }else {
            session.invalidate();
        }
        return login;
    }

    /**学生加入班级*/
    @PutMapping("/students/updateClass")
    public CommonResult updateClass(@RequestBody Student student){
        return studentService.updateClass(student);
    }

    /**获取学生id*/
    @GetMapping("/students/getId")
    public Long getId(HttpSession session){
        System.out.println(session.getAttribute("id"));
        return (Long) session.getAttribute("id");
    }
}
