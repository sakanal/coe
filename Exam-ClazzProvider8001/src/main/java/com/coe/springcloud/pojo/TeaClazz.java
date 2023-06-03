package com.coe.springcloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaClazz {
    private Integer clazzId;//班级号
    private Integer  teacherId;
}
