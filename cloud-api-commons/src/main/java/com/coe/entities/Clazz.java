package com.coe.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clazz {
    private Integer id;//班级号
    private Integer teacherNumber;//班级教师数
    private Integer studentNumber;//班级学生数
    private List<Long> teacherIds;//班级教师id组
    private List<Teacher> teacherList;//班级教师组
    private List<Long> studentIds;//班级学生id组
    private List<Student> studentList;//班级学生组
}
