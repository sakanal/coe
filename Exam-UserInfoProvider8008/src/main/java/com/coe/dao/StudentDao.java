package com.coe.dao;

import com.coe.entities.Student;
import com.coe.pojo.TeaCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 学生模块数据层
 */
@Mapper
public interface StudentDao {

    /**保存一条学生信息到数据库*/
    @Insert("insert into student (id,password,name,birthday,age,sex,phone) values(#{id},#{password},#{name},#{birthday},#{age},#{sex},#{phone})")
    public void save(Student student);

    /**根据id修改一条学生信息*/
    @Update("update student set password = #{password},birthday = #{birthday} , age = #{age} , sex = #{sex} , phone = #{phone} where id = #{id}")
    public void update(Student student);

    /**根据id返回一条学生信息*/
    @Result(column = "clazz_id",property = "clazzId")
    @Select("select * from student where id = #{id}")
    public Student getById(Long id);

    /**根据id验证password*/
    @Select("select id from student where id = #{id} and password = #{password} limit 1")
    public Long login(Student student);

    /**根据id插入修改学生信息clazz_id*/
    @Update("update student set clazz_id = #{clazzId} where id = #{id}")
    public void updateClass(Student student);


    /**查看全部没有加入班级的学生*/
    @Select("select * from student where clazz_id is null ")
    public List<Student> getStudentAll();



}
