package com.coe.dao;

import com.coe.entities.Teacher;
import com.coe.pojo.TeaCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 教师模块数据层
 */
@Mapper
public interface TeacherDao {

    /**保存一条教师信息到数据库*/
    @Insert("insert into teacher (id,password,name,birthday,age,sex,phone) values(#{id},#{password},#{name},#{birthday},#{age},#{sex},#{phone})")
    public void save(Teacher teacher);

    /**根据id修改一条教师信息*/
    @Update("update teacher set password = #{password},birthday = #{birthday} , age = #{age} , sex = #{sex} , phone = #{phone} where id = #{id}")
    public void update(Teacher teacher);

    /**根据id返回一条教师信息*/
    @Select("select * from teacher where id = #{id}")
    public Teacher getById(Long id);

    /**根据id验证password*/
    @Select("select id from teacher where id = #{id} and password = #{password} limit 1")
    public Long login(Teacher teacher);

    /**查看全部教师*/
    @Select("select * from teacher")
    public List<Teacher> getTeacherAll();

    /**教师创建课程*/
    @Insert("insert into tea_course (course_id,teacher_id) values ( #{courseId},#{teacherId} )")
    public void saveCourse(TeaCourse teaCourse);
}
