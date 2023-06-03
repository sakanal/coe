package com.coe.service;

import com.coe.entities.Exam;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface ExamService {
    public PageInfo<Exam> getExams(Integer pageNo);
    public PageInfo<Exam> getExamsByCondition(Exam exam,Integer PageNo);
    public Exam getExamById(Integer id);
    public List<Integer> getExamIdList();
    public String getCourseIdById(Integer id);
    public Integer getQuantityById(Integer id);
    public Integer getStateById(Integer id);

    public void changeState(Integer id);
    public boolean createExam(Exam exam);
    public boolean createExamFromOldExam(Exam oldExam,
                                         Long teacherId,
                                         Integer clazzId,
                                         String courseId,
                                         Integer quantity,
                                         String toJsonQuestionIds,
                                         Timestamp startTimestamp,
                                         Timestamp endTimestamp,
                                         Integer reQuestion);
    public boolean updateExam(Exam exam);

    public boolean deleteExamById(Integer id);
//    public String getQuestion(Integer id);
}
