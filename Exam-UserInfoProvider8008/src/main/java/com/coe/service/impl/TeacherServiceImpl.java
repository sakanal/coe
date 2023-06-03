package com.coe.service.impl;

import com.coe.dao.StudentDao;
import com.coe.dao.TeacherDao;
import com.coe.entities.Student;
import com.coe.entities.Teacher;
import com.coe.pojo.TeaCourse;
import com.coe.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师模块业务层
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    /**教师注册*/
    @Override
    public boolean save(Teacher teacher) {
        teacherDao.save(teacher);
        return true;
    }

    /**教师修改个人信息*/
    @Override
    public boolean update(Teacher teacher) {
        teacherDao.update(teacher);
        return true;
    }

    /**教师查看个人信息*/
    @Override
    public Teacher getById(Long id) {
        return teacherDao.getById(id);
    }

    /**教师登录验证*/
    @Override
    public boolean login(Teacher teacher) {
        return teacherDao.login(teacher) != null;
    }

    @Override
    public List<Teacher> getTeacherAll() {
        return teacherDao.getTeacherAll();
    }
    /**教师创建课程*/
    public boolean saveCourse(TeaCourse teaCourse){
        teacherDao.saveCourse(teaCourse);
        return true;
    }
}
