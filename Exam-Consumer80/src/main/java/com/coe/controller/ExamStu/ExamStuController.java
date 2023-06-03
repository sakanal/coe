package com.coe.controller.ExamStu;

import cn.hutool.json.JSONUtil;
import com.coe.entities.*;
import com.coe.service.CourseService;
import com.coe.service.ExamStuService;
import com.coe.service.ExamTchService;
import com.coe.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ExamStuConsumer")
@Slf4j
public class ExamStuController {
    @Resource
    private ExamStuService examStuService;
    @Resource
    private ExamTchService examTchService;
    @Resource
    private StudentService studentService;
    @Resource
    private CourseService courseService;

    @GetMapping("/")
    public ModelAndView toIndex(){
//        httpSession.setAttribute("id","2019213106");
        return new ModelAndView("/ExamStu/index");
    }
    @GetMapping("/toExamList")
    public ModelAndView toExamList(){
        return new ModelAndView("/ExamStu/Exam/examList");
    }
    @GetMapping("/Exam/{id}")
    public ModelAndView toExam(@PathVariable("id")Integer examId,
                               HttpSession session){
        ModelAndView modelAndView = new ModelAndView("/ExamStu/Exam/examInfo");
        CommonResult<Exam> commonResult = examStuService.getExamById(examId);

        Long studentId =(Long) session.getAttribute("id");

        if (commonResult.getCode()==200){
            Integer countScoreNum = examStuService.countScoreNum(studentId, examId, null, null);
            //表中无成绩，才可以参加考试
            //countScoreNum==0
            if (countScoreNum==0) {
                Exam exam = commonResult.getData();
                Map<String, Question> questionMap = exam.getQuestionMap();
                for (int i=1;i<=exam.getQuantity();i++){
                    Question question = questionMap.get(String.valueOf(i));
                    question.setAnswers(null);
                    question.setRealAnswer(null);
                    question.setAnalysis(null);
                }
                Long teacherId = exam.getTeacherId();
                CommonResult<Teacher> byTeacherId = studentService.getByTeacherId(teacherId);

                exam.setQuestionMap(questionMap);
                exam.setTeacher(byTeacherId.getData());

                modelAndView.addObject("examInfo",exam);
                modelAndView.addObject("questionMap",exam.getQuestionMap());
                modelAndView.addObject("message",commonResult.getMessage());
            }else {
                modelAndView.addObject("errorMessage","考试已完成");
                modelAndView.setViewName("/ExamStu/444");
            }
        }else {
            modelAndView.addObject("errorMessage",commonResult.getMessage());
            modelAndView.setViewName("/ExamStu/444");
        }
        return modelAndView;
    }


    @PostMapping("/overExam")
    public ModelAndView overExam(@RequestParam("examId")Integer examId,
                                // @RequestParam("studentId")Long studentId,
                                 HttpServletRequest httpServletRequest){
        log.info("正在批卷...");
        ModelAndView modelAndView = new ModelAndView("forward:/ExamStuConsumer/toScore");
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        //<题目id，选择的答案值>
        Map<String, String> map = new HashMap<>();
        while (parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            if (!"examId".equals(name) && !"studentId".equals(name)){
                String value = httpServletRequest.getParameter(name);
                map.put(name,value);
            }
        }

        Long studentId =(Long) httpServletRequest.getSession().getAttribute("id");
//        log.info("学生id为-->"+id);
//        Long studentId=Long.parseLong(id);

        String toJsonStr = JSONUtil.toJsonStr(map);
        Integer realQuantity = map.size();

        CommonResult<Score> commonResult = examStuService.correctExam(examId, studentId, toJsonStr,realQuantity);
        if (commonResult.getCode()==200){
            modelAndView.addObject("score",commonResult.getData());
        }else{
            modelAndView.addObject("code",commonResult.getCode());
            modelAndView.addObject("errorMessage",commonResult.getMessage());
            modelAndView.setViewName("/ExamStu/444");
        }
        return modelAndView;
    }

    @RequestMapping("/toScore")
    public ModelAndView getScore(@RequestParam("examId")Integer examId,
                                 @RequestParam(value = "studentId",required = false)Long id,
                                 HttpSession httpSession){
        Long studentId=(Long) httpSession.getAttribute("id");
        if (id!=null && id!=0){
            studentId=id;
        }
        ModelAndView modelAndView = new ModelAndView("/ExamStu/Exam/score");
//        CommonResult<List<Score>> scoreList = examStuService.getScore(studentId, examId, null, null);
        CommonResult<Score> scoreCommonResult = examStuService.getAnswers(studentId, examId);
        if(scoreCommonResult.getCode()==200){
            Score score = scoreCommonResult.getData();
            CommonResult<Exam> examById = examTchService.getExamById(examId);
            if (examById.getCode()==200){
                Map<String, String> answerMap = score.getAnswerMap();
                log.info(String.valueOf(answerMap));
//                for(int i=0;i<answerMap.size();i++){
//                    log.info(answerMap.get(String.valueOf(i)));
//                }
//                log.info(String.valueOf(score));
//                score.getCourseId();
//                Course course = courseService.getByIdCourse(score.getCourseId());
//                modelAndView.addObject("course",course);
                modelAndView.addObject("score",score);
                modelAndView.addObject("examInfo",examById.getData());
                modelAndView.addObject("questionMap",examById.getData().getQuestionMap());
                Map<String, Question> questionMap = examById.getData().getQuestionMap();
                for(int i=0;i<questionMap.size();i++){
                    Question question = questionMap.get(String.valueOf(i));
                    log.info(String.valueOf(question));
                }

            }else {
                modelAndView.addObject("errorMessage",examById.getMessage());
                modelAndView.setViewName("/ExamStu/444");;
                return modelAndView;
            }
        }else {
            modelAndView.addObject("errorMessage",scoreCommonResult.getMessage());
            modelAndView.setViewName("/ExamStu/444");;
            return modelAndView;
        }


        return modelAndView;
    }

    @GetMapping("/toSuccess.html")
    public ModelAndView toSuccess(){
        return new ModelAndView("/ExamStu/success");
    }
    @GetMapping("/toLose.html")
    public ModelAndView toLose(){
        return new ModelAndView("/ExamStu/lose");
    }
}
