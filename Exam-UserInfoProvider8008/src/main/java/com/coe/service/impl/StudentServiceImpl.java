package com.coe.service.impl;

import com.coe.dao.StudentDao;
import com.coe.entities.Student;
import com.coe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生模块业务层
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    /**学生注册*/
    @Override
    public boolean save(Student student) {
        studentDao.save(student);
        return true;
    }

    /**学生修改个人信息*/
    @Override
    public boolean update(Student student) {
        studentDao.update(student);
        return true;
    }

    /**学生查询个人信息*/
    @Override
    public Student getById(Long id) {
        return studentDao.getById(id);
    }

    /**学生登录验证*/
    @Override
    public boolean login(Student student) {
        return studentDao.login(student) != null;
    }

    /**学生加入班级*/
    @Override
    public boolean updateClass(Student student) {
        studentDao.updateClass(student);
        return true;
    }

    /**返回全部没有加入班级的学生*/
    @Override
    public List<Student> getStudentAll() {
        return studentDao.getStudentAll();
    }
}
