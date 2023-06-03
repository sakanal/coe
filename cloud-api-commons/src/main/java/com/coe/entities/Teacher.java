package com.coe.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private Long id;//教师id
    private String password;//教师密码
    private String name;//教师姓名
    private Date birthday;//教师生日
    private Integer age;//教师年龄
    private String sex;//教师性别
    private Long phone;//教师电话
}
