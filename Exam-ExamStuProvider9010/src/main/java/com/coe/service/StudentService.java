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

    /**学生查询个人信息*/
    @GetMapping("/students/getById/{id}")
    public CommonResult<Student> getByStudentId(@PathVariable("id") Long id);

    /**教师根据id查询个人信息*/
    @GetMapping("/teachers/getById/{id}")
    public CommonResult<Teacher> getByTeacherId(@PathVariable("id") Long id);

}
