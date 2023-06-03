package com.coe.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coe.entities.*;
import com.coe.service.CourseService;
import com.coe.service.ExamService;
import com.coe.service.ExamStuService;
import com.coe.service.StudentService;
import com.coe.utils.ExamUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/ExamStu")
@Slf4j
public class ExamStuController {
    @Resource
    private ExamService examService;

    @Resource
    private ExamStuService examStuService;

    @Resource
    private StudentService studentService;

    @Resource
    private CourseService courseService;

    @GetMapping("/getMyExamList")
    public CommonResult<PageInfo<Exam>> getMyExamList(@RequestParam(value = "clazzId",required = false)Integer clazzId,
                                                      @RequestParam(value = "state",required = false)Integer state,
                                                      @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo){

        log.info(String.valueOf(clazzId));
        log.info(String.valueOf(state));
        log.info(String.valueOf(pageNo));
        CommonResult<PageInfo<Exam>> commonResult = examService.getExamsByCondition(clazzId, state, pageNo);
        if (commonResult.getCode()==200){
            return commonResult;
        }else {
            return commonResult;
        }
    }
    @GetMapping("/getExamById")
    public CommonResult<Exam> getExamById(@RequestParam("examId")Integer examId){
        CommonResult<Integer> examStateById = examService.getExamStateById(examId);
        Integer state = examStateById.getData();
        if (examStateById.getCode()==200){
            if (state!=2){
                CommonResult<Exam> commonResult = examService.getExamById(examId);
                if (commonResult.getCode()==200){
                    Exam exam = commonResult.getData();
                    if (exam.getState()==1){
                        log.info("message-->"+commonResult.getMessage());
                        /*Map<String, Question> questionMap = exam.getQuestionMap();
                        for (int i=1;i<=exam.getQuantity();i++){
                            Question question = questionMap.get(String.valueOf(i));
                            question.setAnswers(null);
                            question.setRealAnswer(null);
                            question.setAnalysis(null);
//                            question.setCourse(null);
//                            question.setCourseId(null);
                        }
                        exam.setQuestionMap(questionMap);*/
                        return new CommonResult<>(200,"获取考试试卷成功",exam);
                    }else if (exam.getState()==2){
                        return new CommonResult<>(444,"考试已经结束");
                    }else if(exam.getState()==0){
                        return new CommonResult<>(444,"考试尚未开始，请稍后再试");
                    } else {
                        return new CommonResult<>(444,"考试数据错误，请联系管理员");
                    }
                }else {
                    return commonResult;
                }
            }else {
                return new CommonResult<>(444,"考试已结束");
            }
        }else {
            return new CommonResult<>(examStateById.getCode(),examStateById.getMessage());
        }
    }

    @PostMapping("/getExamAnswers")
    public CommonResult<Exam> getAnswer(@RequestParam("examId")Integer examId){
        CommonResult<Exam> commonResult = examService.getExamById(examId);
        log.info(commonResult.getMessage());
        if (commonResult.getCode()==200){
            Exam exam = commonResult.getData();
            log.info(String.valueOf(exam));
            Map<String, Question> questionMap = exam.getQuestionMap();
            for (int i=1;i<=questionMap.size();i++){
                Question question = questionMap.get(String.valueOf(i));
                Map<String, Answer> answersMap = question.getAnswersMap();
                for (int j=0;j<answersMap.size();j++){
                    log.info(String.valueOf(answersMap.get(String.valueOf(j))));
                }
                log.info("正确答案-->"+question.getRealAnswer());
            }
            return commonResult;
        }else {
            return commonResult;
        }
    }

    @PostMapping("/correctExam")
    public CommonResult<Score> correctExam(@RequestParam("examId")Integer examId,
                                           @RequestParam("studentId")Long studentId,
                                           @RequestParam("toJsonStr")String answerString,
                                           @RequestParam("realQuantity")Integer realQuantity){
        CommonResult<Exam> commonResult = examService.getExamById(examId);
        if (commonResult.getCode()==200){
            Exam exam = commonResult.getData();
            log.info(String.valueOf(exam));
            //数据转换json-->map
            //学生选择的答案
            JSONObject jsonObject = JSONUtil.parseObj(answerString);
            //<试卷题号，答案实际值>
            Map<String, String> map = new HashMap<>();
            for (int i=1;i<=exam.getQuantity();i++){
                log.info(String.valueOf(i));
//                log.info(String.valueOf(exam.getQuantity()));
                log.info("realQuantity-->"+realQuantity);
                log.info(String.valueOf(i<=realQuantity));
                if (i<=realQuantity){
                    String str = jsonObject.getStr(String.valueOf(i));
                    map.put(String.valueOf(i),str);
                    log.info("put-->"+str);
                }else {
                    map.put(String.valueOf(i),null);
                    log.info("put-->null");
                }
            }

            Score score = examStuService.correctExam(map, exam, studentId, realQuantity, answerString);

            if (score!=null){
                return new CommonResult<>(200,"批改试卷完成",score);

            }else {
                return new CommonResult<>(444,"批改试卷失败，请联系管理人员");
            }
        }else {
            return new CommonResult<>(commonResult.getCode(), commonResult.getMessage());
        }
    }


    @PostMapping("/countScoreNum")
    public Integer countScoreNum(@RequestParam(value = "studentId",required = false)Long studentId,
                                 @RequestParam(value = "examId",required = false)Integer examId,
                                 @RequestParam(value = "courseId",required = false)String courseId,
                                 @RequestParam(value = "clazzId",required = false)Integer clazzId){

        return examStuService.countScoreNum(studentId, examId, courseId, clazzId);
    }

    @PostMapping("/getScore")
    public CommonResult<List<Score>> getScore(@RequestParam(value = "studentId",required = false)Long studentId,
                                              @RequestParam(value = "examId",required = false)Integer examId,
                                              @RequestParam(value = "courseId",required = false)String courseId,
                                              @RequestParam(value = "clazzId",required = false)Integer clazzId){
        log.info("成绩提供方开始获取成绩列表...");
        if (studentId!=null && studentId!=0){
            log.info("studentId-->"+studentId);
        }
        if (examId!=null && examId!=0){
            log.info("examId-->"+examId);
        }
        if (courseId!=null && !"".equals(courseId)){
            log.info("courseId-->"+courseId);
        }
        if (clazzId!=null && clazzId!=0) {
            log.info("clazzId-->"+clazzId);
        }
        List<Score> scores = examStuService.getScore(studentId, examId, courseId, clazzId);
        List<Score> scoreList=new ArrayList<>();
        if (scores!=null && scores.size()>0){
            for (Score score : scores) {
                Long scoreStudentId = score.getStudentId();
                String scoreCourseId = score.getCourseId();
                CommonResult<Student> byStudentId = studentService.getByStudentId(scoreStudentId);
                Course course = courseService.getByIdCourse(scoreCourseId);
                score.setStudent(byStudentId.getData());
                score.setCourse(course);
                scoreList.add(score);
            }
            return new CommonResult<>(200,"获取成绩列表成功",scoreList);
        }else {
            return new CommonResult<>(444,"暂无成绩信息");
        }
    }

    @PostMapping("/getStuAnswers")
    public CommonResult<Score> getAnswers(@RequestParam(value = "studentId",required = false)Long studentId,
                                          @RequestParam(value = "examId",required = false)Integer examId){
        Score score = examStuService.getAnswers(studentId, examId);
        if (score!=null){
            return new CommonResult<>(200,"获取答案成功",score);
        }else {
            return new CommonResult<>(444,"暂无成绩信息");
        }
    }
}
