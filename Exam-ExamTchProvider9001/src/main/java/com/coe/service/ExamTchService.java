package com.coe.service;

import com.coe.entities.CommonResult;
import com.coe.entities.Exam;
import com.coe.service.fallback.ExamTchServiceFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "EXAMPROVIDER",fallback = ExamTchServiceFallback.class)
public interface ExamTchService {

    @GetMapping("/getExamList")
    public CommonResult<PageInfo<Exam>> getExamList(@RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo);

    @PostMapping("/getExams")
    public CommonResult<PageInfo<Exam>> getExamsByCondition(@RequestParam(value = "clazzId",required = false)Integer clazzId,
                                                            @RequestParam(value = "courseId",required = false)String courseId,
                                                            @RequestParam(value = "state",required = false)Integer state,
                                                            @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo);

    @GetMapping("/Exam/{id}")
    public CommonResult<Exam> getExamById(@PathVariable("id")Integer id);

    @PostMapping("/Exam")
    public CommonResult<Boolean> createExam(@RequestParam("teacherId") Long teacherId,
                                            @RequestParam("clazzId") Integer clazzId,
                                            @RequestParam("courseId") String courseId,
                                            @RequestParam("quantity") Integer quantity,
                                            @RequestParam("startTime") String startTimeString,
                                            @RequestParam("endTime") String endTimeString);

    @PostMapping("/createExamFormOld")
    public CommonResult<Boolean> createExamFormOld(@RequestParam("examId")Integer examId,//试卷id
                                                   @RequestParam(value = "teacherId") Long teacherId,//教师id
                                                   @RequestParam(value = "clazzId",required = false) Integer clazzId,//班级id
                                                   @RequestParam(value = "courseId",required = false) String courseId,//课程id
                                                   @RequestParam(value = "quantity",required = false) Integer quantity,//题目量
                                                   @RequestParam(value = "startTime",required = false) String startTimeString,//考试开始时间
                                                   @RequestParam(value = "endTime",required = false) String endTimeString,//考试结束时间
                                                   @RequestParam(value = "reQuestion",required = false)Integer reQuestion/*是否重新随机出题*/);
    @PutMapping("/Exam")
    public CommonResult<Boolean> updateExam(@RequestParam("examId")Integer examId,//试卷id
                                            @RequestParam(value = "teacherId",required = false) Long teacherId,//教师id
                                            @RequestParam(value = "clazzId",required = false) Integer clazzId,//班级id
                                            @RequestParam(value = "courseId",required = false) String courseId,//课程id
                                            @RequestParam(value = "quantity",required = false) Integer quantity,//题目量
                                            @RequestParam(value = "startTime",required = false) String startTimeString,//考试开始时间
                                            @RequestParam(value = "endTime",required = false) String endTimeString,//考试结束时间
                                            @RequestParam(value = "reQuestion",required = false)Integer reQuestion/*是否重新随机出题：0:不获取题目，1:重新获取题目*/);

    @DeleteMapping("/Exam/{id}")
    public CommonResult<Boolean> deleteExamById(@PathVariable("id")Integer id);



    //通用功能
    @RequestMapping("/getExamStateById")
    public CommonResult<Integer> getExamStateById(@RequestParam("examId")Integer id);


}
