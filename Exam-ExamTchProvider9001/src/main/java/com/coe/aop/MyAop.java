package com.coe.aop;

import com.coe.entities.Exam;
import com.coe.service.ExamService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j
public class MyAop {
    @Resource
    private ExamService examService;
    @Pointcut("execution(public * com.coe.controller.ExamController.get*(..))")
    public void setLog(){}

//    @Before(value = "setLog()")
//    public void beforeChangeState(JoinPoint joinPoint){
//        Object[] args = joinPoint.getArgs();
//        if (args.length>0){
//            log.info(Arrays.toString(args));
//        }
//        List<Exam> exams = examService.getExams();
//        for (Exam exam : exams) {
//            examService.changeState(exam.getId());
//        }
//    }
}
