package com.coe.service.fallback;

import com.coe.entities.CommonResult;
import com.coe.entities.Exam;
import com.coe.service.ExamTchService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class ExamTchServiceFallback implements ExamTchService {
    @Override
    public CommonResult<PageInfo<Exam>> getExamList(Integer pageNo) {
        return new CommonResult<>(444,"运行ExamTchService服务的fallback方法，无法获取到试卷列表，Exam-ExamTchProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<PageInfo<Exam>> getExamsByCondition(Integer clazzId, String courseId, Integer state, Integer pageNo) {
        return new CommonResult<>(444,"运行ExamTchService服务的fallback方法，无法搜索到试卷，Exam-ExamTchProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Exam> getExamById(Integer id) {
        return new CommonResult<>(444,"运行ExamTchService服务的fallback方法，无法获取到试卷的详细信息，Exam-ExamTchProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Boolean> createExam(Long teacherId, Integer clazzId, String courseId, Integer quantity, String startTimeString, String endTimeString) {
        return new CommonResult<>(444,"运行ExamTchService服务的fallback方法，无法创建试卷，Exam-ExamTchProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Boolean> createExamFormOld(Integer examId, Long teacherId, Integer clazzId, String courseId, Integer quantity, String startTimeString, String endTimeString, Integer reQuestion) {
        return new CommonResult<>(444,"运行ExamTchService服务的fallback方法，无法复制试卷，Exam-ExamTchProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Boolean> updateExam(Integer examId, Long teacherId, Integer clazzId, String courseId, Integer quantity, String startTimeString, String endTimeString, Integer reQuestion) {
        return new CommonResult<>(444,"运行ExamTchService服务的fallback方法，无法更新试卷，Exam-ExamTchProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Boolean> deleteExamById(Integer id) {
        return new CommonResult<>(444,"运行ExamTchService服务的fallback方法，无法删除试卷，Exam-ExamTchProvider模块未启动，请稍后再试");
    }

    @Override
    public CommonResult<Integer> getExamStateById(Integer id) {
        return new CommonResult<>(444,"运行ExamTchService服务的fallback方法，无法获取试卷状态，Exam-ExamTchProvider模块未启动，请稍后再试");
    }
}
