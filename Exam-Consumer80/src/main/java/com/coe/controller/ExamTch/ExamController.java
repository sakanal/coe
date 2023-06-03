package com.coe.controller.ExamTch;

import com.coe.entities.CommonResult;
import com.coe.entities.Exam;
import com.coe.entities.Question;
import com.coe.entities.Teacher;
import com.coe.service.ExamTchService;
import com.coe.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/ExamConsumer")
public class ExamController {
    @Resource
    private ExamTchService examTchService;
    @Resource
    private StudentService studentService;

    @RequestMapping("/")
    public ModelAndView toIndex(){
//        session.setAttribute("id","100001");
        return new ModelAndView("/ExamTch/index");
    }
    @GetMapping("/toExamList")
    public ModelAndView toExamList(){
        return new ModelAndView("/ExamTch/Exam/examList");
    }
    @GetMapping("/toMyExamList")
    public ModelAndView toMyExamList(){
        return new ModelAndView("/ExamTch/Exam/MyExamList");
    }
    @GetMapping("/toCreateExam")
    public ModelAndView toCreateExam(){
        return new ModelAndView("/ExamTch/Exam/createExam");
    }


    @GetMapping("/Exam/{id}")
    public ModelAndView getExamById(@PathVariable("id")Integer id){
        ModelAndView modelAndView = new ModelAndView("/ExamTch/Exam/examInfo");
        CommonResult<Exam> commonResultExam = examTchService.getExamById(id);
        if (commonResultExam.getCode()==200){
            Map<String, Question> questionMap = commonResultExam.getData().getQuestionMap();

            for(int i=1;i<=commonResultExam.getData().getQuantity();i++){
                log.info(String.valueOf(questionMap.get(String.valueOf(i))));
            }
            CommonResult<Teacher> byTeacherId = studentService.getByTeacherId(commonResultExam.getData().getTeacherId());

            commonResultExam.getData().setTeacher(byTeacherId.getData());
//            CommonResult<Exam> temp = new CommonResult<>(commonResultExam.getCode(),commonResultExam.getMessage(),commonResultExam.getData());

            modelAndView.addObject("examInfo",commonResultExam.getData());
            modelAndView.addObject("teacherInfo",byTeacherId.getData());
            modelAndView.addObject("questionMap",questionMap);
        }else {
            modelAndView.setViewName("/test/444");
            modelAndView.addObject("errorCode",commonResultExam.getCode());
            modelAndView.addObject("errorMessage",commonResultExam.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/toExam/{id}")
    public ModelAndView toExam(@PathVariable("id")Integer examId,
                               HttpSession session){
        session.setAttribute("examId",examId);
        return new ModelAndView("/ExamTch/Exam/newExamInfo");
    }





//    @DeleteMapping("/Exam/{id}")
//    public boolean deleteExamById(@PathVariable("id")Integer id){
//        return examService.deleteExamById(id);
//    }

}
