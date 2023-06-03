package com.coe.service;

import com.coe.entities.Exam;
import com.coe.entities.Score;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface ExamStuService {
    public Score correctExam(Map<String,String> map,
                               Exam exam,
                               Long studentId,
                               Integer realQuantity,
                               String answerString);
    public Integer countScoreNum(Long studentId,
                                 Integer examId,
                                 String courseId,
                                 Integer clazzId);

    public List<Score> getScore(Long studentId,
                                Integer examId,
                                String courseId,
                                Integer clazzId);
    public Score getAnswers(Long studentId,
                            Integer examId);
}
