package com.coe.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private Integer id;//问题id
    private Long teacherId;//出题教师id
    private String question;//题目题干
    private String answers;//json类型答案组，用于获取数据库中的json类型数据，同时用于将Map<String,Answer>类型数据装为String存入数据库中（JSONUtil.toJsonStr(obj)）
    private Map<String,Answer> answersMap;//map类型答案组,Map<(A/B/C/D),Answer>，将属性Answers使用JSONUtil.parseObj(answers).getStr(key)获取Answer放入该属性中

    private List<Answer> answerList;

    private String realAnswer;//用于记录四个选项中的正确选项号A/B/C/D，为Map<String,Answer> answersMap中的String

    //private String realAnswer;//json类型正确答案，用于获取数据库中的json类型数据，同时用于将Answer类型数据装为String存入数据库中（JSONUtil.toJsonStr）
    //private Answer answer;//正确答案，使用JSONUtil.toBean(realAnswer, Answer.class)将realAnswer转为Answer类型放入属性中
    private String analysis;//解析
    private Course course;//课程
    private String courseId;
}
