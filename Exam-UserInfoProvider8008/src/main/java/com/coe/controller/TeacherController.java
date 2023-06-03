package com.coe.controller;

import com.coe.entities.CommonResult;
import com.coe.entities.Teacher;
import com.coe.pojo.TeaCourse;
import com.coe.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师模块表现层
 */
@RestController
@RequestMapping("/teachers")
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**教师注册*/
    @PostMapping("/save")
    public CommonResult save(@RequestBody Teacher teacher){
        boolean flag = teacherService.save(teacher);
        Integer code = flag ? 200:404;
        String message = flag ? "注册成功":"注册失败";
        return new CommonResult(code,message);
    }

    /**教师修改个人信息*/
    @PutMapping("/update")
    public CommonResult update(@RequestBody Teacher teacher){
        boolean flag = teacherService.update(teacher);
        Integer code = flag ? 200:404;
        String message = flag ? "修改成功":"修改失败";
        return new CommonResult(code,message);
    }

    /**教师查询个人信息*/
    @GetMapping("/getById/{id}")
    public CommonResult getByTeacherId(@PathVariable Long id){
        Teacher teacher = teacherService.getById(id);
        Integer code = teacher != null ? 200:404;
        String message = teacher != null ? "查询成功":"查询失败";
        return new CommonResult(code,message,teacher);
    }

    /**教师登录验证*/
    @PostMapping("/login")
    public Boolean login(@RequestBody Teacher teacher){
        System.out.println(teacher.getId()+teacher.getPassword());
        boolean flag = teacherService.login(teacher);
        return  flag;
    }
    /**查看全部教师*/
    @GetMapping("/getTeacherAll")
    public List<Teacher> getTeacherAll(){
        log.info("服务提供--》获取所有教师");
        return teacherService.getTeacherAll();
    }
    /**教师创建课程*/
    @PostMapping("/saveCourse")
    public CommonResult saveCourse(@RequestBody TeaCourse teaCourse){
        boolean flag = teacherService.saveCourse(teaCourse);
        Integer code = flag ? 200:404;
        String message = flag ? "添加成功":"添加失败";
        return new CommonResult(code,message);
    }
}
