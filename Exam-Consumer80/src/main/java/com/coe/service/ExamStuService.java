package com.coe.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coe.entities.*;
import com.coe.service.fallback.ExamStuProviderFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@FeignClient(value = "ExamStuProvider",fallback = ExamStuProviderFallback.class)
public interface ExamStuService {

    @GetMapping("/ExamStu/getMyExamList")
    public CommonResult<PageInfo<Exam>> getMyExamList(@RequestParam(value = "clazzId",required = false)Integer clazzId,
                                                      @RequestParam(value = "state",required = false)Integer state,
                                                      @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo);
    @GetMapping("/ExamStu/getExamById")
    public CommonResult<Exam> getExamById(@RequestParam("examId")Integer examId);

    @PostMapping("/ExamStu/getExamAnswers")
    public CommonResult<Exam> getAnswer(@RequestParam("examId")Integer examId);

    @PostMapping("/ExamStu/correctExam")
    public CommonResult<Score> correctExam(@RequestParam("examId")Integer examId,
                                           @RequestParam("studentId")Long studentId,
                                           @RequestParam("toJsonStr")String answerString,
                                           @RequestParam("realQuantity")Integer realQuantity);

    @PostMapping("/ExamStu/countScoreNum")
    public Integer countScoreNum(@RequestParam(value = "studentId",required = false)Long studentId,
                                 @RequestParam(value = "examId",required = false)Integer examId,
                                 @RequestParam(value = "courseId",required = false)String courseId,
                                 @RequestParam(value = "clazzId",required = false)Integer clazzId);

    @PostMapping("/ExamStu/getScore")
    public CommonResult<List<Score>> getScore(@RequestParam(value = "studentId",required = false)Long studentId,
                                              @RequestParam(value = "examId",required = false)Integer examId,
                                              @RequestParam(value = "courseId",required = false)String courseId,
                                              @RequestParam(value = "clazzId",required = false)Integer clazzId);

    @PostMapping("/ExamStu/getStuAnswers")
    public CommonResult<Score> getAnswers(@RequestParam(value = "studentId",required = false)Long studentId,
                                          @RequestParam(value = "examId",required = false)Integer examId);
}
