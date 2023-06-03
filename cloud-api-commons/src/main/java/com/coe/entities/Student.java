package com.coe.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private long id;//学生id
    private String password;//学生密码
    private String name;//学生姓名
    private Date birthday;//学生生日
    private Integer age;//学生年龄
    private String sex;//学生性别
    private Long phone;//学生电话
    private Integer clazzId;//学生班级号
    private List<Course> courses;//学生所学课程
}
