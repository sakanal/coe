package com.coe.mapper;

import com.coe.entities.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ExamMapper {
    public List<Exam> getExams();
    public List<Exam> getExamsByCondition(Exam exam);
    public Exam getExamById(@Param("id")Integer id);
    public String getQuestion(@Param("id")Integer id);
    public List<Integer> getExamIdList();
    public String getCourseIdById(@Param("id")Integer id);
    public Integer getQuantityById(@Param("id")Integer id);
    public Integer getStateById(@Param("id")Integer id);

    public boolean changeState(@Param("id")Integer id,@Param("state")Integer state);
    public boolean createExam(Exam exam);
    public int countByExamId(@Param("id") Integer id);

    public boolean updateExam(Exam exam);
//    public boolean updateTime(@Param("startTime")Timestamp startTime, @Param("endTime")Timestamp endTime, @Param("time")Time time);

    public boolean deleteExamById(@Param("id")Integer id);
}
