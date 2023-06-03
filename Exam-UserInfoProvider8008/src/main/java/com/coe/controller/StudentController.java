package com.coe.controller;

import com.coe.entities.CommonResult;
import com.coe.entities.Student;
import com.coe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生模块表现层
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**学生注册*/
    @PostMapping("/save")
    public CommonResult save(@RequestBody Student student){
        boolean flag = studentService.save(student);
        Integer code = flag ? 200:404;
        String message = flag ? "注册成功":"注册失败";
        return new CommonResult(code,message);
    }

    /**学生修改个人信息*/
    @PutMapping("/update")
    public CommonResult update(@RequestBody Student student){
        boolean flag = studentService.update(student);
        Integer code = flag ? 200:404;
        String message = flag ? "修改成功":"修改失败";
        return new CommonResult(code,message);
    }

    /**学生查询个人信息*/
    @GetMapping("/getById/{id}")
    public CommonResult getByStudentId(@PathVariable Long id){
        Student student = studentService.getById(id);
        Integer code = student != null ? 200:404;
        String message = student != null ? "查询成功":"查询失败";
        return new CommonResult(code,message,student);
    }

    /**学生登录验证*/
    @PostMapping("/login")
    public Boolean login(@RequestBody Student student){
        boolean flag = studentService.login(student);
        return  flag;
    }

    /**学生加入班级*/
    @PutMapping("/updateClass")
    public CommonResult updateClass(@RequestBody Student student){
        boolean flag = studentService.updateClass(student);
        Integer code = flag ? 200:404;
        String message = flag ? "修改成功":"修改失败";
        return new CommonResult(code,message);
    }


    /**返回全部没有加入班级的学生*/
    @GetMapping("/getStudentAll")
    public List<Student> getStudentAll(){
        return studentService.getStudentAll();
    }
}
