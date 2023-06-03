package com.coe.mapper;

import com.coe.entities.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamStuMapper {
    public boolean insertScore(Score score);
    public Integer countScoreNum(@Param("id")Integer id,
                                 @Param("studentId") Long studentId,
                                 @Param("examId")Integer examId,
                                 @Param("courseId")String courseId,
                                 @Param("clazzId")Integer clazzId);

    public List<Score> getScore(@Param("studentId") Long studentId,
                                @Param("examId")Integer examId,
                                @Param("courseId")String courseId,
                                @Param("clazzId")Integer clazzId);

    public Score getAnswers(@Param("studentId")Long studentId,
                            @Param("examId")Integer examId);
}
