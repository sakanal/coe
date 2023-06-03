package com.coe.controller.ExamStu;

import com.coe.entities.*;
import com.coe.service.ExamStuService;
import com.coe.service.StudentService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ExamStuConsumer")
@Slf4j
public class ExamStuRestController {
    @Resource
    private ExamStuService examStuService;
    @Resource
    private StudentService studentService;

    @GetMapping("getExamList")
    public CommonResult<PageInfo<Exam>> getExamList(@RequestParam(value = "state",required = false)Integer state,
                                                    @RequestParam(value = "pageNo",required = false)Integer pageNo,
                                                    HttpSession httpSession){
        Long studentId =(Long) httpSession.getAttribute("id");
        log.info("session中的studentId为："+studentId);
//        Long studentId=Long.parseLong(id);
        CommonResult<Student> commonResult = studentService.getByStudentId(studentId);
        Student student = commonResult.getData();
        Integer clazzId = student.getClazzId();
        if (clazzId!=null){
            log.info(String.valueOf(clazzId));
            CommonResult<PageInfo<Exam>> myExamList = examStuService.getMyExamList(clazzId, state, pageNo);
            if (myExamList.getCode()==200){
                List<Exam> exams = myExamList.getData().getList();
                List<Exam> examList=new ArrayList<>();
                for (Exam exam : exams) {
                    CommonResult<Teacher> byTeacherId = studentService.getByTeacherId(exam.getTeacherId());
                    exam.setTeacher(byTeacherId.getData());
                    examList.add(exam);
                }
                myExamList.getData().setList(examList);
                return myExamList;
            }else {
                return new CommonResult<>(444,"暂无试卷");
            }
        }else {
            return new CommonResult<>(444,"该学生未加入任何班级");
        }

    }
    @GetMapping("getStudent")
    public CommonResult<Student> getStudent(HttpSession session){
        Long studentId =(Long) session.getAttribute("id");
//        Long studentId=Long.parseLong(id);
        CommonResult<Student> commonResult = studentService.getByStudentId(studentId);
        Student student = commonResult.getData();

        return new CommonResult<>(200,"获取学生基本信息成功",student);
    }

    @PostMapping("/getScore")
    public CommonResult<Score> getScore(@RequestParam(value = "examId",required = false)Integer examId,
                                        @RequestParam(value = "courseId",required = false)String courseId,
                                        @RequestParam(value = "clazzId",required = false)Integer clazzId,
                                        HttpSession session){

        Long studentId =(Long) session.getAttribute("id");
//        Long studentId=Long.parseLong(id);
//        log.info("studentId-->"+studentId);

//        if (examId!=null && examId!=0){
//            log.info("examId-->"+examId);
//        }
//        if (courseId!=null && !"".equals(courseId)){
//            log.info("courseId-->"+courseId);
//        }
//        if (clazzId!=null && clazzId!=0) {
//            log.info("clazzId-->"+clazzId);
//        }
//        CommonResult<List<Score>> score = examStuService.getScore(studentId, examId, courseId, clazzId);
        CommonResult<Score> score = examStuService.getAnswers(studentId, examId);
        if (score.getData()!=null){
            return new CommonResult<>(score.getCode(), score.getMessage(), score.getData());
        }else {
            return new CommonResult<>(444,"暂无成绩信息");
        }
    }

}
