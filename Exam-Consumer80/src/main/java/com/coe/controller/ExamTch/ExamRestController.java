package com.coe.controller.ExamTch;

import com.coe.entities.CommonResult;
import com.coe.entities.Course;
import com.coe.entities.Exam;
import com.coe.entities.Teacher;
import com.coe.service.CourseService;
import com.coe.service.ExamTchService;
import com.coe.service.StudentService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/ExamConsumer")
public class ExamRestController {
    @Resource
    private ExamTchService examTchService;
    @Resource
    private CourseService courseService;
    @Resource
    private StudentService studentService;

    @GetMapping("/getExamList")
    public CommonResult<PageInfo<Exam>> getExamList(@RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo){
        log.info("start getExamList方法");
        CommonResult<PageInfo<Exam>> commonResult = examTchService.getExamList(pageNo);

        List<Exam> exams = commonResult.getData().getList();
        List<Exam> examList = new ArrayList<>();
        for (Exam exam : exams) {
            String courseId = exam.getCourseId();
            Course course = courseService.getByIdCourse(courseId);
            exam.setCourse(course);
            examList.add(exam);
        }
        commonResult.getData().setList(examList);

        if (commonResult.getCode()==200){
            return commonResult;
        }else {
            return new CommonResult<>(commonResult.getCode(),commonResult.getMessage());
        }
    }
    @PostMapping("/getExamsByCondition")
    public CommonResult<PageInfo<Exam>> getExamsByCondition(@RequestParam(value = "clazzId",required = false)Integer clazzId,
                                                            @RequestParam(value = "courseId",required = false)String courseId,
                                                            @RequestParam(value = "state",required = false)Integer state,
                                                            @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo){
        CommonResult<PageInfo<Exam>> commonResultExamsByCondition = examTchService.getExamsByCondition(clazzId, courseId,  state, pageNo);

        List<Exam> exams = commonResultExamsByCondition.getData().getList();
        List<Exam> examList = new ArrayList<>();
        for (Exam exam : exams) {
            String courseIds = exam.getCourseId();
            Long teacherId = exam.getTeacherId();
            Course course = courseService.getByIdCourse(courseIds);
            CommonResult<Teacher> byTeacherId = studentService.getByTeacherId(teacherId);
            exam.setCourse(course);
            exam.setTeacher(byTeacherId.getData());
            examList.add(exam);
        }
        commonResultExamsByCondition.getData().setList(examList);
        if (commonResultExamsByCondition.getCode()==200){
            return commonResultExamsByCondition;
        }else {
            return new CommonResult<>(commonResultExamsByCondition.getCode(),commonResultExamsByCondition.getMessage());
        }
    }

    //旧版-普通的获取信息
    @GetMapping("/getExamById/{id}")
    public CommonResult<Exam> getExamById(@PathVariable("id")Integer id){
        CommonResult<Exam> commonResultExam = examTchService.getExamById(id);
        if (commonResultExam.getCode()==200){

            return commonResultExam;
        }else {
            return new CommonResult<>(commonResultExam.getCode(),commonResultExam.getMessage());
        }
    }
    //新版-教师查看考试页面所用
    @GetMapping("/getExamByExamId")
    public CommonResult<Exam> getExamByExamId(HttpSession session){
        Integer examId =(Integer) session.getAttribute("examId");
        CommonResult<Exam> commonResult = examTchService.getExamById(examId);
        if (commonResult.getCode()==200){
            return commonResult;
        }else {
            return new CommonResult<>(commonResult.getCode(),commonResult.getMessage());
        }
    }


    @PostMapping("/Exam")
    public CommonResult<Boolean> createExam(@RequestParam("clazzId") Integer clazzId,
                                            @RequestParam("courseId") String courseId,
                                            @RequestParam("quantity") Integer quantity,
                                            @RequestParam("startTime") String startTimeString,
                                            @RequestParam("endTime") String endTimeString,
                                            HttpSession session){
        Long teacherId =(Long) session.getAttribute("id");
//        Long teacherId=Long.parseLong(id);

        if (clazzId!=null){
            if (courseId!=null && !"".equals(courseId)){
                if (quantity!=null){
                    if (startTimeString!=null && !"".equals(startTimeString)){
                        if (endTimeString!=null && !"".equals(endTimeString)) {
                            return examTchService.createExam(teacherId,clazzId,courseId,quantity,startTimeString,endTimeString);
                        }else{
                            return new CommonResult<>(444,"考试结束时间未提供，请重新输入数据");
                        }
                    }else {
                        return new CommonResult<>(444,"考试时间开始时间未提供，请重新输入数据");
                    }
                }else {
                    return new CommonResult<>(444,"题目量为空，请重新输入数据");
                }
            }else {
                return new CommonResult<>(444,"课程号为空，请重新输入数据");
            }
        }else {
            return new CommonResult<>(444,"班级号为空，请重新输入数据");
        }
    }


    @PostMapping("/createExamFormOld")
    public CommonResult<Boolean> createExamFormOld(@RequestParam("examId")Integer examId,//试卷id
                                                   @RequestParam(value = "teacherId") Long teacherId,//教师id
                                                   @RequestParam(value = "clazzId",required = false) Integer clazzId,//班级id
                                                   @RequestParam(value = "courseId",required = false) String courseId,//课程id
                                                   @RequestParam(value = "quantity",required = false) Integer quantity,//题目量
                                                   @RequestParam(value = "startTime",required = false) String startTimeString,//考试开始时间
                                                   @RequestParam(value = "endTime",required = false) String endTimeString,//考试结束时间
                                                   @RequestParam(value = "reQuestion",required = false)Integer reQuestion/*是否重新随机出题*/,
                                                   HttpSession session){

        if (examId!=null){
            if (teacherId!=null){
                return examTchService.createExamFormOld(examId,teacherId,clazzId,courseId,quantity,startTimeString,endTimeString,reQuestion);
            }else {
                return new CommonResult<>(444,"出题教师id未提供，请检查登录信息");
            }
        }else {
            return new CommonResult<>(444,"试卷模板id未提供，无法获取试卷模板，请稍后再试");
        }
    }



    @PutMapping("/Exam")
    public CommonResult<Boolean> updateExam(@RequestParam("examId")Integer examId,//试卷id
                                            @RequestParam(value = "teacherId",required = false) Long teacherId,//教师id
                                            @RequestParam(value = "clazzId",required = false) Integer clazzId,//班级id
                                            @RequestParam(value = "courseId",required = false) String courseId,//课程id
                                            @RequestParam(value = "quantity",required = false) Integer quantity,//题目量
                                            @RequestParam(value = "startTime",required = false) String startTimeString,//考试开始时间
                                            @RequestParam(value = "endTime",required = false) String endTimeString,//考试结束时间
                                            @RequestParam(value = "reQuestion",required = false)Integer reQuestion/*是否重新随机出题：0:不获取题目，1:重新获取题目*/,
                                            HttpSession session){
        if (examId!=null && examId!=0) {
            CommonResult<Integer> commonResultState = examTchService.getExamStateById(examId);
            if (commonResultState.getCode()==200) {
                Integer examState = commonResultState.getData();
                if (examState!=0){
                    //无法修改试卷
                    if (examState==1){
                        return new CommonResult<>(444,"考试正在进行，请不要修改试卷信息");
                    }else if (examState==2){
                        return new CommonResult<>(444,"考试已结束，无法对试卷信息进行修改--功能未开放");
                    }else {
                        return new CommonResult<>(444,"试卷状态错误，请联系管理人员");
                    }
                }else {
                    //可以修改试卷
                    CommonResult<Boolean> commonResult = examTchService.updateExam(examId, teacherId, clazzId, courseId, quantity, startTimeString, endTimeString, reQuestion);
                    if (commonResult.getCode()==200){
                        return new CommonResult<>(200,"试卷修改成功",true);
                    }else{
                        return new CommonResult<>(444,"试卷修改失败，请检查数据是否有误");
                    }
                }
            }else {
                //服务调用产生的错误
                return new CommonResult<>(444,commonResultState.getMessage());
            }
        }else {
            return new CommonResult<>(444,"试卷id未提供，无法对试卷进行修改，请稍后再试");
        }
    }


    //test
    @DeleteMapping("/deleteExamById")
    public CommonResult<Boolean> deleteExamById(@RequestParam("examId")Integer id){
        return examTchService.deleteExamById(id);
    }




}
