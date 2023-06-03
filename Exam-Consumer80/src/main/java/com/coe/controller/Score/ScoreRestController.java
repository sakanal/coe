package com.coe.controller.Score;

import com.coe.entities.CommonResult;
import com.coe.entities.Score;
import com.coe.service.ExamStuService;
import com.coe.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Score")
@Slf4j
public class ScoreRestController {
    @Resource
    private StudentService studentService;
    @Resource
    private ExamStuService examStuService;

    @GetMapping("/getScoreList")
    public CommonResult<List<Score>> getScoreList(@RequestParam("clazzId")Integer clazzId,
                                                  @RequestParam("examId")Integer examId){
        if (clazzId!=null && clazzId!=0){
            if (examId!=null && examId!=0){
                return examStuService.getScore(null, examId, null, clazzId);
            }else {
                return new CommonResult<>(444,"未提供试卷id");
            }
        }else {
            return new CommonResult<>(444,"未提供班级号");
        }
    }
}
