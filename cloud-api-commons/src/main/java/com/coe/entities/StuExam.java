package com.coe.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuExam {
    private Long studentId;
    private Integer examId;
    private String answerString;
    private Integer quantity;
    //<试卷题目号，选择的答案>
    private Map<String,String> answerMap;
}
