package com.coe.mapper;

import com.coe.entities.Course;
import com.coe.entities.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    public Course getCourseById(@Param("id")String id);
    public Course getCourseIdByTeacherId(@Param("teacherId")Long teacherId);
    public List<Long> getTeacherIdsByCourseId(@Param("courseId")String courseId);
}
