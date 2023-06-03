package com.coe;

import com.coe.entities.Exam;
import com.coe.mapper.ExamMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ExamProvider9001ApplicationTests {
    @Resource
    private ExamMapper examMapper;

    @Test
    void contextLoads() {
        PageHelper.startPage(1,5);
        List<Exam> exams = examMapper.getExams();
        PageInfo<Exam> examPageInfo = new PageInfo<>(exams);
        System.out.println("examPageInfo = " + examPageInfo);
        System.out.println(examPageInfo.getPages());
    }

}
