package com.coe.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private String id;//课程id
    private String name;//课程名称
    private List<Teacher> teacherList;//教师列表
//    private long teacherId;//教师id
}
