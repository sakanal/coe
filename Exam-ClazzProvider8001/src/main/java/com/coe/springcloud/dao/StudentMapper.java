package com.coe.springcloud.dao;

import com.coe.entities.Student;
import com.coe.springcloud.pojo.AddStudent;
import com.coe.springcloud.pojo.studentinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    public List<Long> getStudentIdsByclazzId(@Param("clazzId") Integer clazzId);
    public List<Student> getStudentByStudentId(@Param("studentIds") List<Long>studentIds);
    public Student getStudentById(@Param("studentId")Long studentId);
    public Integer getClazzStuNumber(@Param("id")Integer id );

    public boolean DeleteStubyId(Integer id);
    public boolean addStudent(AddStudent addStudent);
    public List<studentinfo> getAllstudent();
}
