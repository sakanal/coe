package com.coe.controller;

import com.coe.entities.CommonResult;
import com.coe.entities.Question;
import com.coe.entities.Teacher;
import com.coe.service.*;
import com.coe.entities.Exam;
import com.coe.utils.ExamUtils;
import com.coe.utils.TimeUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ExamController {
    @Resource
    private ExamService examService;
    @Resource
    private QuestionService questionService;
    @Resource
    private ClazzService clazzService;
    @Resource
    private StudentService studentService;

    @GetMapping("/getExamList")
    public CommonResult<PageInfo<Exam>> getExamList(@RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo){
        log.info("Exam-Provider9001--->getExamList方法");
        log.info(String.valueOf(pageNo));
        List<Integer> examIdList = examService.getExamIdList();
        for (Integer examId : examIdList) {
            examService.changeState(examId);
        }
        PageInfo<Exam> pageInfo = examService.getExams(pageNo);
        if (pageInfo!=null){
            List<Exam> exams = pageInfo.getList();
            List<Exam> examList = new ArrayList<>();
            for (Exam exam : exams) {
                //根据教师id获取教师信息
                Long teacherId = exam.getTeacherId();
                CommonResult<Teacher> byTeacherId = studentService.getByTeacherId(teacherId);
                log.info(String.valueOf(byTeacherId));
                if (byTeacherId.getCode()==200){
                    exam.setTeacher(byTeacherId.getData());
                }
                //根据班级id获取班级信息
                Integer clazzId = exam.getClazzId();
                examList.add(exam);
            }
            return new CommonResult<>(200,"获取试卷基础信息成功！！",pageInfo);
        }else {
            return new CommonResult<>(444,"获取试卷基础信息失败，暂无数据");
        }

    }

    @PostMapping("/getExams")
    public CommonResult<PageInfo<Exam>> getExamsByCondition(@RequestParam(value = "clazzId",required = false)Integer clazzId,
                                                            @RequestParam(value = "courseId",required = false)String courseId,
                                                            @RequestParam(value = "state",required = false)Integer state,
                                                            @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo){
        log.info("Exam-Provider9001--->getExamsByCondition方法");

        Exam exam = new Exam(null, null,null, clazzId,null, courseId, null, null, null, null, null, null, null, state);
        PageInfo<Exam> examsByCondition = examService.getExamsByCondition(exam, pageNo);
        if (examsByCondition!=null){
            return new CommonResult<>(200,"获取试卷基础信息成功！！",examsByCondition);
        }else {
            return new CommonResult<>(444,"暂无数据，请更换搜索条件");
        }
    }

    @GetMapping("/Exam/{id}")
    public CommonResult<Exam> getExamById(@PathVariable("id")Integer id){
        //更新试卷状态
        examService.changeState(id);
        //获取试卷信息，包括基础信息以及题目id组
        Exam exam = examService.getExamById(id);
        if (exam!=null) {
            //获取该试卷的题目id组，将String型题目组id转为List<Integer>型
            List<Integer> questionIds = ExamUtils.getQuestions(exam);

            List<Question> questionList;
            //test
            //获取完整题目组
            if (questionIds!=null) {
                //根据问题id组获取问题组
                //test
//                questionList = TempUtils.getBathByIds(exam.getQuantity());
                questionList = questionService.getBatchByIds(questionIds);
                if (questionList!=null) {
                    exam.setQuestion(null);
                    //将List型问题组转为Map<String,Question>型，放入Exam试卷中
                    exam=ExamUtils.getCompleteExam(questionList,exam);
                    if (exam!=null) {
//                        根据教师id获取教师信息
                        Long teacherId = exam.getTeacherId();
                        CommonResult<Teacher> byTeacherId = studentService.getByTeacherId(teacherId);
                        if (byTeacherId.getData()!=null){
                            exam.setTeacher(byTeacherId.getData());
                        }
                        return new CommonResult<>(200,"获取id为"+id+"的试卷详细信息成功！！",exam);
                    }else {
                        return new CommonResult<>(444,"试卷总题目数与实际题目数不匹配，请联系管理人员");
                    }
                }else {
                    return new CommonResult<>(444,"试卷详细题目获取失败，请联系管理人员");
                }
            }else {
                return new CommonResult<>(444, "试卷题目获取失败，请联系管理人员");
            }
        }
        return new CommonResult<>(444,"试卷获取失败，请检查数据是否有误");
    }

    @PostMapping("/Exam")
    public CommonResult<Boolean> createExam(@RequestParam("teacherId") Long teacherId,
                                            @RequestParam("clazzId") Integer clazzId,
                                            @RequestParam("courseId") String courseId,
                                            @RequestParam("quantity") Integer quantity,
                                            @RequestParam("startTime") String startTimeString,
                                            @RequestParam("endTime") String endTimeString){
        Exam exam;
        log.info("start createExam...");
        log.info(String.valueOf(teacherId));
        //随机获取题目List<String>
        //test
//        List<Integer> randomQuestionIds = TempUtils.getRandomQuestion(quantity);
        List<Integer> randomQuestionIds = questionService.getRand(courseId, quantity);
        if (randomQuestionIds!=null){
            Timestamp startTimestamp = TimeUtils.parseTimestamp(startTimeString);
//            log.info("startTimestamp-->"+startTimestamp);
            Timestamp endTimestamp = TimeUtils.parseTimestamp(endTimeString);
//            log.info("endTimestamp-->"+endTimestamp);
            String timeString = TimeUtils.getDistanceTime(startTimeString, endTimeString);
            Time time = TimeUtils.parseTime(timeString);
//            log.info("time-->"+time);
            if ("00:00:00".equals(String.valueOf(time))){
                return new CommonResult<>(444,"考试时间过长，请确认考试时间");
            }
            String jsonQuestionIds = ExamUtils.toJsonQuestionIds(randomQuestionIds);

            exam=new Exam(ExamUtils.getSimpleUUID(),teacherId,null,clazzId,null,courseId,null,startTimestamp,endTimestamp,time,quantity,jsonQuestionIds,null,0);

        }else {
            return new CommonResult<>(444,"试卷题目获取失败，请重新再试");
        }

        if (examService.createExam(exam)){
            return new CommonResult<>(200,"创建试卷成功！！", true);
        }else {
            return new CommonResult<>(444,"创建试卷失败，请重新再试");
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
                                     @RequestParam(value = "reQuestion",required = false)Integer reQuestion/*是否重新随机出题*/){
        //考试已经结束的情况下只能创建新的试卷，可以以该试卷为模板      需要更改试卷id
        Exam oldExam = examService.getExamById(examId);
        Timestamp startTimestamp;
        Timestamp endTimestamp;
        String toJsonQuestionIds;

        if (oldExam!=null) {
            //转换格式
            startTimestamp = TimeUtils.parseTimestamp(startTimeString);
            endTimestamp = TimeUtils.parseTimestamp(endTimeString);
            //获取新题目的标记为false，根据其他条件判断是否需要获取新的题目
            //课程id与试卷模板不同
            //题目量与试卷模板不同
            log.info("reQuestion"+reQuestion);
            if (reQuestion!=1) {
                //获取到了课程id
                if (courseId!=null && !"".equals(courseId)){
                    //课程id为新值，必须要重新获取题目
                    if (!oldExam.getCourseId().equals(courseId)){
                        reQuestion=1;
                        log.info("新试卷课程与试卷模板的课程不同，需要重新获取题目数据");
                    }
                }
                //题目量与试卷模板不同，必须要重新获取题目
                if (quantity!=null && !oldExam.getQuantity().equals(quantity)){
                    reQuestion=1;
                    log.info("新试卷题目量与试卷模板不同，需要重新获取题目数据");
                }
            }
            List<Integer> randQuestion;
            toJsonQuestionIds = oldExam.getQuestion();
            //判断是否需要重新获取题目
            log.info("reQuestion"+reQuestion);
            if (toJsonQuestionIds!=null) {
                if (reQuestion!=0){
                    //课程id为新值/null
                    if (!oldExam.getCourseId().equals(courseId)){
                        //携带了题量，使用该值作为重新获取的题目量
                        if (quantity!=null){
                            //携带了课程id
                            if (courseId!=null && !"".equals(courseId)){
                                //课程id不为空，且课程id为新值
                                //test
                                randQuestion=questionService.getRand(courseId,quantity);
//                                randQuestion=TempUtils.getRandomQuestion(quantity);
                                log.info("使用新课程id与新题目量获取题目数据");
                            }else {
                                //课程id
                                //test
                                randQuestion=questionService.getRand(oldExam.getCourseId(),quantity);
//                                randQuestion=TempUtils.getRandomQuestion(quantity);
                                log.info("使用试卷模板的题目id与新题目量获取题目数据");
                            }
                        }else {//未携带题目量，使用试卷模板的题目量
                            //携带了课程id
                            if (courseId!=null && !"".equals(courseId)){
                                //test
                                randQuestion=questionService.getRand(courseId,oldExam.getQuantity());
//                                randQuestion=TempUtils.getRandomQuestion(oldExam.getQuantity());
                                log.info("使用新题目id与试卷模板题目量获取题目数据");
                            }else {
                                //test
                                randQuestion=questionService.getRand(oldExam.getCourseId(),oldExam.getQuantity());
//                                randQuestion=TempUtils.getRandomQuestion(oldExam.getQuantity());
                                log.info("使用试卷模板课程id与试卷模板题目量获取题目数据");
                            }
                        }
                        //课程id为旧值，使用试卷模板的课程id，可以使用试卷模板的题目
                    }else {
                        if (quantity!=null){
                            //test
                            randQuestion = questionService.getRand(oldExam.getCourseId(), quantity);
//                            randQuestion=TempUtils.getRandomQuestion(quantity);
                            log.info("使用试卷模板课程id与新题目量获取题目数据");
                        }else {
                            //test
                            randQuestion=questionService.getRand(oldExam.getCourseId(),oldExam.getQuantity());
//                            randQuestion=TempUtils.getRandomQuestion(oldExam.getQuantity());
                            log.info("使用试卷模板课程id与试卷模板题目量获取题目数据");
                        }
                    }

                    if (randQuestion!=null && randQuestion.size()>0) {
                        //将List<Integer>型问题id组转为类似json的String型数据
                        toJsonQuestionIds=ExamUtils.toJsonQuestionIds(randQuestion);
                        log.info("转换List<Integer>题目id组");
                    }else {
                        return new CommonResult<>(444,"服务调用失败，试卷题目获取失败，请重新再试");
                    }
                }
            }else {
                return new CommonResult<>(444,"试卷模板损坏，请更换试卷模板");
            }
        }else {
            return new CommonResult<>(444,"无法获取到试卷模板，请联系管理人员");
        }
//        Exam exam = new Exam(ExamUtils.getSimpleUUID(),teacherId,clazzId,courseId,null,startTimestamp,endTimestamp,null,quantity,null,null,0);
        if (examService.createExamFromOldExam(oldExam, teacherId, clazzId, courseId, quantity, toJsonQuestionIds, startTimestamp, endTimestamp, reQuestion)){
            return new CommonResult<>(200,"根据试卷模板创建试卷成功！！", true);
        }else{
            return new CommonResult<>(444,"创建试卷失败，请重新再试");
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
                              @RequestParam(value = "reQuestion",required = false)Integer reQuestion/*是否重新随机出题：0:不获取题目，1:重新获取题目*/){
        log.info("examId-->"+examId);
        log.info("teacherId-->"+teacherId);
        log.info("clazzId-->"+clazzId);
        log.info("courseId-->"+courseId);
        log.info("quantity-->"+quantity);
        log.info("startTime-->"+startTimeString);
        log.info("endTime-->"+endTimeString);
        log.info("reQuestion-->"+reQuestion);
        //考试未开始才能更改试卷的所有信息（exam.state=0）   不需要更改试卷id
        //修改试卷所属班级··
        //修改试卷的开始和结束时间-计算出考试时间··
        //修改试卷题目，不修改课程··
        //修改课程，重新获取试卷题目··
        Timestamp startTimestamp = TimeUtils.parseTimestamp(startTimeString);//
        Timestamp endTimestamp = TimeUtils.parseTimestamp(endTimeString);//
        Exam exam = new Exam(examId,null,null,clazzId,null,courseId,null,startTimestamp,endTimestamp,null,quantity,null,null,0);

        String courseIdById = examService.getCourseIdById(examId);
        Integer quantityById = examService.getQuantityById(examId);
        if (reQuestion==0) {
            if (courseId!=null && !"".equals(courseId)){
                if (!courseId.equals(courseIdById)){
                    reQuestion=1;
                }
            }
            if (quantity!=null && !quantity.equals(0)){
                if (!quantity.equals(quantityById)){
                    reQuestion=1;
                }
            }
        }

        //根据试卷id获取原始课程id，之后比对，判断是否需要重新获取题目
        if (reQuestion!=0/* || !Objects.equals(courseId, courseIdById) || !Objects.equals(quantity, quantityById)*/){
            List<Integer> randQuestionIds = null;
            //重新获取题目
            //test
            if (courseId!=null && !"".equals(courseId)) {
                if (quantity!=null){
                    randQuestionIds = questionService.getRand(courseId, quantity);
                }else {
                    randQuestionIds=questionService.getRand(courseId,examService.getQuantityById(examId));
                }
            }else {
                if (quantity!=null){
                    randQuestionIds=questionService.getRand(examService.getCourseIdById(examId),quantity);
                }else {
                    randQuestionIds=questionService.getRand(examService.getCourseIdById(examId),examService.getQuantityById(examId));
                }
            }
            /*if (quantity!=null){
                randQuestionIds = TempUtils.getRandomQuestion(quantity);
            }else {
                randQuestionIds = TempUtils.getRandomQuestion(examService.getQuantityById(examId));
            }*/
//            log.info("randSize"+randQuestionIds.size());
            if (randQuestionIds!=null && randQuestionIds.size()==quantity){
//                log.info("quantity"+quantity);
                String toJsonQuestionIds = ExamUtils.toJsonQuestionIds(randQuestionIds);
                exam.setQuestion(toJsonQuestionIds);
            }else {
                return new CommonResult<>(444,"重新获取题目失败，请再试一次");
            }
        } //不需要获取题目

        if (examService.updateExam(exam)){
            return new CommonResult<>(200,"更新试卷成功！！",true);
        }else {
            return new CommonResult<>(444,"更新试卷失败，请再试一次");
        }
//        return true;
    }

    @DeleteMapping("/Exam/{id}")
    public CommonResult<Boolean> deleteExamById(@PathVariable("id")Integer id){
        log.info("id-->"+id);
        Integer stateById = examService.getStateById(id);
        if (stateById==0){
            if (examService.deleteExamById(id)){
                return new CommonResult<>(200,"删除试卷成功！！",true);
            }else {
                return new CommonResult<>(444,"删除试卷失败，请检查数据是否有误");
            }
        }else if (stateById==1){
            return new CommonResult<>(444,"考试正在进行中，请不要删除试卷");
        }else if (stateById==2){
            return new CommonResult<>(444,"考试已结束，请不要删除试卷");
        }else {
            return new CommonResult<>(444,"数据异常，请联系管理员");
        }
    }





    //通用功能
    @RequestMapping("/getExamStateById")
    public CommonResult<Integer> getExamStateById(@RequestParam("examId")Integer id){
        log.info("examId"+id);
        Integer stateById = examService.getStateById(id);
        if (stateById!=null){
            return new CommonResult<>(200,"获取试卷状态码成功！！",stateById);
        }else {
            return new CommonResult<>(444,"获取试卷状态码失败，请检查数据信息，再试一次");
        }
    }
}
