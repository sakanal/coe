package com.coe.service;

import com.coe.entities.Student;

import java.util.List;

/**
 * 学生模块业务层接口
 */
public interface StudentService {

    /**学生注册*/
    public boolean save(Student student);

    /**学生修改个人信息*/
    public boolean update(Student student);

    /**学生查询个人信息*/
    public Student getById(Long id);

    /**学生登录验证*/
    public boolean login(Student student);

    /**学生加入班级*/
    public boolean updateClass(Student student);

    /**返回全部没有加入班级的学生*/
    public List<Student> getStudentAll();
}
