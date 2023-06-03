package com.coe.service;

import com.coe.entities.CommonResult;
import com.coe.entities.Exam;
import com.coe.service.fallback.ExamServiceFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "EXAMPROVIDER")
public interface ExamService {

    @GetMapping("/getExamList")
    public CommonResult<PageInfo<Exam>> getExamList(@RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo);

    @PostMapping("/getExams")
    public CommonResult<PageInfo<Exam>> getExamsByCondition(@RequestParam(value = "clazzId",required = false)Integer clazzId,
                                                            @RequestParam(value = "state",required = false)Integer state,
                                                            @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo);

    @GetMapping("/Exam/{id}")
    public CommonResult<Exam> getExamById(@PathVariable("id")Integer id);




    //通用功能
    @RequestMapping("/getExamStateById")
    public CommonResult<Integer> getExamStateById(@RequestParam("examId")Integer id);
}
