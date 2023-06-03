package com.coe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class studentinfo {

    private long id;//学生id
    private String name;//学生姓名
    private Integer clazz_id;//学生班级号
}