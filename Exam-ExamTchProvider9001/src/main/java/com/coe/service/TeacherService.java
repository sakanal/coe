package com.coe.service;


import com.coe.entities.CommonResult;
import com.coe.entities.Teacher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public interface TeacherService {


    /**教师注册*/
    @PostMapping("/teachers/save")
    public CommonResult save(@RequestBody Teacher teacher);

    /**教师修改个人信息*/
    @PutMapping("/teachers/update")
    public CommonResult update(@RequestBody Teacher teacher);

    /**教师根据id查询个人信息*/
    @GetMapping("/teachers/getById/{id}")
    public CommonResult<Teacher> getById(@PathVariable("id") Long id);

    /**教师登录验证*/
    @PostMapping("/teachers/login")
    public Boolean login(@RequestBody Teacher teacher);

    /**查看全部教师*/
    @GetMapping("/teachers/getTeacherAll")
    public List<Teacher> getTeacherAll();
}
