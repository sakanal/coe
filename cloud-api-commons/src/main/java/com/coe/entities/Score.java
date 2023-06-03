package com.coe.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private Integer id;//成绩id
    private Long studentId;//学生id
    private Student student;
    private Integer examId;//试卷id
    private String courseId;//课程id
    private Course course;
    private Integer clazzId;//班级id
    private Integer score;//成绩
    private Integer quantity;
    private String answerString;
    private Map<String,String> answerMap;//学生在该试卷选择的答案，Map<试卷中的题号，答案的实际值>

    public Score(Integer id, Long studentId, Integer examId, String courseId, Integer clazzId, Integer score, Integer quantity, String answerString, Map<String, String> answerMap) {
        this.id = id;
        this.studentId = studentId;
        this.examId = examId;
        this.courseId = courseId;
        this.clazzId = clazzId;
        this.score = score;
        this.quantity = quantity;
        this.answerString = answerString;
        this.answerMap = answerMap;
    }
}
