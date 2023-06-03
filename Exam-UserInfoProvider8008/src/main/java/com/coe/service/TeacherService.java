package com.coe.service;

import com.coe.entities.Teacher;
import com.coe.pojo.TeaCourse;

import java.util.List;

/**
 * 教师模块业务层接口
 */
public interface TeacherService {

    /**教师注册*/
    public boolean save(Teacher teacher);

    /**教师修改个人信息*/
    public boolean update(Teacher teacher);

    /**教师查看个人信息*/
    public Teacher getById(Long id);

    /**教师登录验证*/
    public boolean login(Teacher teacher);

    /**返回全部教师信息*/
    public List<Teacher> getTeacherAll();

    /**教师创建课程*/
    public boolean saveCourse(TeaCourse teaCourse);
}
