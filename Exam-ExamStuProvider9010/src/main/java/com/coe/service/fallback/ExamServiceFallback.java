package com.coe.service.fallback;

import com.coe.entities.CommonResult;
import com.coe.entities.Exam;
import com.coe.service.ExamService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class ExamServiceFallback implements ExamService {
    @Override
    @GetMapping("/getExamList")
    public CommonResult<PageInfo<Exam>> getExamList(@RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo) {
        return new CommonResult<>(444,"Exam-Provider-getExams服务获取失败，请稍后再试");
    }

    @Override
    public CommonResult<PageInfo<Exam>> getExamsByCondition(Integer clazzId,Integer state, Integer pageNo) {
        return new CommonResult<>(444,"Exam-Provider-getExamsByCondition服务获取失败，请稍后再试");
    }


    @Override
    public CommonResult<Exam> getExamById(Integer id) {
        return new CommonResult<>(444,"Exam-Provider-getExamById服务获取失败，请稍后再试");
    }


    @Override
    public CommonResult<Integer> getExamStateById(Integer id) {
        return new CommonResult<>(444,"Exam-Provider-getExamStateById服务获取失败，请稍后再试");
    }
}
