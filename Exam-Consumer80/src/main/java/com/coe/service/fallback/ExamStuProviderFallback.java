package com.coe.service.fallback;

import com.coe.entities.CommonResult;
import com.coe.entities.Exam;
import com.coe.entities.Score;
import com.coe.service.ExamStuService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExamStuProviderFallback implements ExamStuService {
    @Override
    public CommonResult<PageInfo<Exam>> getMyExamList(Integer clazzId, Integer state, Integer pageNo) {
        return new CommonResult<>(444,"运行ExamStuService服务的fallback方法，获取本班试卷失败，Exam-ExamStuProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Exam> getExamById(Integer examId) {
        return new CommonResult<>(444,"运行ExamStuService服务的fallback方法，获取试卷基础信息失败，Exam-ExamStuProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Exam> getAnswer(Integer examId) {
        return new CommonResult<>(444,"运行ExamStuService服务的fallback方法，获取试卷答案失败，Exam-ExamStuProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Score> correctExam(Integer examId, Long studentId, String answerString, Integer realQuantity) {
        return new CommonResult<>(444,"运行ExamStuService服务的fallback方法，获取试卷答案失败，Exam-ExamStuProvider模块未启动，请稍后再试");
    }

    @Override
    public Integer countScoreNum(Long studentId, Integer examId, String courseId, Integer clazzId) {
        return null;
    }

    @Override
    public CommonResult<List<Score>> getScore(Long studentId, Integer examId, String courseId, Integer clazzId) {
        return new CommonResult<>(444,"运行ExamStuService服务的fallback方法，获取所有成绩失败，Exam-ExamStuProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Score> getAnswers(Long studentId, Integer examId) {
        return new CommonResult<>(444,"运行ExamStuService服务的fallback方法，获取成绩失败，Exam-ExamStuProvider模块未启动，请稍后再试");
    }
}
