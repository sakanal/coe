package com.coe.springcloud.pojo;

import com.coe.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStudent {
    private long id;//学生id
    private String password;//学生密码
    private String name;//学生姓名
    private Integer clazz_id;//学生班级号
}
