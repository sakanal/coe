package com.coe.springcloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryClazz {
    private Integer id;//班级号
    private Integer  cla_tea;
    private Integer  cla_stu;
}
