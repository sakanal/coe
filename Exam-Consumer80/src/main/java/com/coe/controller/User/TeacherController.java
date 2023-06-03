package com.coe.controller.User;

import com.coe.entities.CommonResult;
import com.coe.entities.Teacher;
import com.coe.pojo.TeaCourse;
import com.coe.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
@RestController
@Slf4j
public class TeacherController {
    @Resource
    private StudentService studentService;

    @GetMapping("/toTeacherIndex")
    public ModelAndView toTeacherIndex(){
        return new ModelAndView("/ExamTch/index");
    }

    /**教师个人中心页*/
    @GetMapping("/teachers")
    public ModelAndView getView(){
        return new ModelAndView("/ExamTch/Teacher/teacher");
    }

    /**教师注册*/
    @PostMapping("/teachers/save")
    public CommonResult save(@RequestBody Teacher teacher,HttpSession session){
        Long id = teacher.getId();
        if (id>0){
            String password = teacher.getPassword();
            if (password!=null && !"".equals(password)){
                String name = teacher.getName();
                if (name!=null && !"".equals(name)){
                    session.setAttribute("id",teacher.getId());
                    session.setAttribute("identity",2);
                    return studentService.save(teacher);
                }else {
                    return new CommonResult(444,"未输教师姓名，请重新输入");
                }
            }else {
                return new CommonResult(444,"未输入教师密码，请重新输入");
            }
        }else {
            return new CommonResult(444,"未输入教师编号，请从重新输入");
        }
    }

    /**教师修改个人信息*/
    @PutMapping("/teachers/update")
    public CommonResult update(@RequestBody Teacher teacher){
        return studentService.update(teacher);
    }

    /**教师查询个人信息*/
    @GetMapping("/teachers/getById/{id}")
    public CommonResult getById(@PathVariable Long id){
        return studentService.getByTeacherId(id);
    }

    /**教师登录验证*/
    @PostMapping("/teachers/login")
    public boolean login(@RequestBody Teacher teacher,
                         HttpSession session,
                         HttpServletResponse response){
        System.out.println(teacher);
        Boolean login = studentService.login(teacher);
        if (login){
            session.setAttribute("id",teacher.getId());
            session.setAttribute("identity",2);
        }else {
            session.invalidate();
        }
        return login;
    }

    /**查看全部教师*/
    @GetMapping("/teachers/getTeacherAll")
    public List<Teacher> getTeacherAll(){
        log.info("开始获取所有教师");
        return studentService.getTeacherAll();
    }

    /**获取教师id*/
    @GetMapping("/teachers/getId")
    public Long getId(HttpSession session){
        System.out.println(session.getAttribute("id"));
        return (Long) session.getAttribute("id");
    }

    /**教师创建课程*/
    @PostMapping("/teachers/saveCourse")
    public CommonResult saveCourse(@RequestBody TeaCourse teaCourse, HttpSession session){
        teaCourse.setTeacherId((Long) session.getAttribute("id"));
        String courseId = teaCourse.getCourseId();
        if(courseId!=null && !"".equals(courseId)){
            return studentService.saveCourse(teaCourse);
        }else {
            return new CommonResult(444,"未输入课程编号，请重新输入");
        }
    }
}
