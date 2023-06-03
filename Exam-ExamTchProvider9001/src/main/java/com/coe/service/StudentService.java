package com.coe.service;

import com.coe.entities.CommonResult;
import com.coe.entities.Student;
import com.coe.entities.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "UserProvider")
public interface StudentService {

    /**学生注册*/
    @PostMapping("/students/save")
    public CommonResult save(@RequestBody Student student);

    /**学生修改个人信息*/
    @PutMapping("/students/update")
    public CommonResult update(@RequestBody Student student);

    /**学生查询个人信息*/
    @GetMapping("/students/getById/{id}")
    public CommonResult<Student> getByStudentId(@PathVariable("id") Long id);

    /**学生登录验证*/
    @PostMapping("/students/login")
    public Boolean login(@RequestBody Student student);

    /**学生加入班级*/
    @PutMapping("/students/updateClass")
    public CommonResult updateClass(@RequestBody Student student);

    /**教师注册*/
    @PostMapping("/teachers/save")
    public CommonResult save(@RequestBody Teacher teacher);

    /**教师修改个人信息*/
    @PutMapping("/teachers/update")
    public CommonResult update(@RequestBody Teacher teacher);

    /**教师根据id查询个人信息*/
    @GetMapping("/teachers/getById/{id}")
    public CommonResult<Teacher> getByTeacherId(@PathVariable("id") Long id);

    /**教师登录验证*/
    @PostMapping("/teachers/login")
    public Boolean login(@RequestBody Teacher teacher);

    /**查看全部教师*/
    @GetMapping("/teachers/getTeacherAll")
    public List<Teacher> getTeacherAll();
}
