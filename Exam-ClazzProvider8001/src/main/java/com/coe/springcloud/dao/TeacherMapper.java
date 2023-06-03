package com.coe.springcloud.dao;

import com.coe.entities.Teacher;
import com.coe.springcloud.pojo.TeaClazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherMapper {
    public List<Long> getTeachersIdByClazzId(@Param("clazzId")Integer clazzId);
    public List<Teacher> getTeachersByTeacherId(@Param("teacherIds")List<Long> teacherIds);
    public List<TeaClazz> getclassByteacherid(@Param("id") Long id);
    public List<Teacher> getteacherinfo();
}
