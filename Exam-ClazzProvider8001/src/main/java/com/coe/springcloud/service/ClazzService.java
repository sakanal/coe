package com.coe.springcloud.service;

import com.coe.entities.Clazz;
import com.coe.entities.Teacher;

import com.coe.springcloud.pojo.AddStudent;
import com.coe.springcloud.pojo.QueryClazz;
import com.coe.springcloud.pojo.TeaClazz;
import com.coe.springcloud.pojo.studentinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzService {
    public Clazz getClazzById(Integer id);
    public boolean delelebyid(int id);
    public Integer getClazzTeaNumber(Integer id );
    public Integer getClazzStuNumber(@Param("id")Integer id );
    public boolean updateClazzById(@Param("tnum") Integer tnum,@Param("snum") Integer snum,@Param("id") Integer id);
    public List<Integer> getAllClazzId();

    public List<QueryClazz> getClaInfo();

    public boolean addClazz(Clazz clazz);
    public boolean addTeaClazz(TeaClazz teaClazz);

    public boolean DeleteStubyId(Integer id);
    public boolean addStudent(AddStudent addStudent);

    public List<studentinfo> getAllstudent();
    public List<TeaClazz> getclassByteacherid(@Param("id") Long id);
    public List<Teacher> getteacherinfo();
}
