package com.coe.service.impl;

import com.coe.mapper.ExamMapper;
import com.coe.service.ExamService;
import com.coe.entities.Exam;
import com.coe.utils.ExamUtils;
import com.coe.utils.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ExamServiceImpl implements ExamService {
    @Resource
    private ExamMapper examMapper;
    @Override
    public PageInfo<Exam> getExams(Integer pageNo) {
        //获取试卷的基础信息，不包括json类型的question
        //会获取试卷id，教师id，班级id，课程id，课程，考试开始时间，考试结束时间，考试时间，题目量，考试状态
        //课程会通过association从Course表中获取数据
        PageHelper.startPage(pageNo,ExamUtils.EXAM_PAGESIZE);
        List<Exam> exams = examMapper.getExams();
        if (exams!=null && exams.size()>0){
            return new PageInfo<Exam>(exams, ExamUtils.EXAM_NAVIGATEPAGES);
        }else {
            return null;
        }
    }

    @Override
    public PageInfo<Exam> getExamsByCondition(Exam exam,Integer PageNo) {
        //根据试卷信息进行条件搜索
        //试卷id，教师id，班级id，课程id，题目量，试卷状态
        //当条件不为空或数据有效时，条件有效
        PageHelper.startPage(PageNo,ExamUtils.EXAM_PAGESIZE);
        List<Exam> examsByCondition = examMapper.getExamsByCondition(exam);
        if (examsByCondition!=null && examsByCondition.size()>0){
            return new PageInfo<Exam>(examsByCondition,ExamUtils.EXAM_NAVIGATEPAGES);
        }else {
            return null;
        }
    }

    @Override
    public Exam getExamById(Integer id) {
        if (id!=null){
            //获取试卷的基础信息，不包括json类型的question
            Exam exam = examMapper.getExamById(id);
            if (exam!=null){
                //获取json类型的question
                String question = examMapper.getQuestion(id);
                if (question!=null && !"".equals(question)){
                    exam.setQuestion(question);
                    //返回带问题id组的试卷
                    return exam;
                }
//            exam.setQuestion(examMapper.getQuestion(id));
            }
        }
        return null;
    }

    @Override
    public List<Integer> getExamIdList() {
        return examMapper.getExamIdList();
    }

    @Override
    public String getCourseIdById(Integer id) {
        return id!=null?examMapper.getCourseIdById(id):null;
    }

    @Override
    public Integer getQuantityById(Integer id) {
        return id!=null?examMapper.getQuantityById(id):null;
    }

    @Override
    public Integer getStateById(Integer id) {
        return id!=null?examMapper.getStateById(id):null;
    }

    @Override
    public void changeState(Integer id) {
        Exam exam = examMapper.getExamById(id);
        if (exam!=null) {
            int state=0;
            Timestamp startTime = exam.getStartTime();
            Timestamp endTime = exam.getEndTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp nowTime = Timestamp.valueOf(simpleDateFormat.format(new Date()));

            long flag1 = TimeUtils.compareTime(nowTime, startTime);
            long flag2 = TimeUtils.compareTime(endTime, nowTime);
            if (flag1>0){
                if (flag2>0){
                    state=1;
                }else {
                    state=2;
                }
            }
//            if (state!=0){
//                examMapper.changeState(id,state);
//            }
            examMapper.changeState(id,state);
        }
    }

    @Override
    public boolean createExam(Exam exam) {
        boolean result=false;
        int flag = examMapper.countByExamId(exam.getId());
        while (flag!=0){
            exam.setId(ExamUtils.getSimpleUUID());
            flag=examMapper.countByExamId(exam.getId());
        }
        long timeFlag = TimeUtils.compareTime(exam.getEndTime(), exam.getStartTime());
        if (timeFlag>0){
            result=examMapper.createExam(exam);
        }
        return result;
    }

    @Override
    public boolean createExamFromOldExam(Exam oldExam,
                                         Long teacherId,
                                         Integer clazzId,
                                         String courseId,
                                         Integer quantity,
                                         String toJsonQuestionIds,
                                         Timestamp startTimestamp,
                                         Timestamp endTimestamp,
                                         Integer reQuestion) {
        Exam exam = new Exam();
        //获取新的试卷id
        exam.setId(ExamUtils.getSimpleUUID());
        int flag = examMapper.countByExamId(exam.getId());
        while (flag!=0){
            exam.setId(ExamUtils.getSimpleUUID());
            flag=examMapper.countByExamId(exam.getId());
        }
        //判断使用新旧教师id
        exam.setTeacherId(teacherId);
//        if (teacherId!=null && teacherId!=0L){
//            exam.setTeacherId(teacherId);
//        }else {
//            exam.setTeacherId(oldExam.getTeacherId());
//        }
        //判断使用新旧班级id
        if (clazzId != null) {
            exam.setClazzId(clazzId);
        } else {
            exam.setClazzId(oldExam.getClazzId());
        }
        //判断使用新旧课程id
        if (courseId!=null && !"".equals(courseId)){
            exam.setCourseId(courseId);
        }else {
            exam.setCourseId(oldExam.getCourseId());
        }
        //判断使用新旧开始时间、结束时间、考试时间
        if (startTimestamp!=null && endTimestamp!=null){
            String distanceTime = TimeUtils.getDistanceTime(startTimestamp,endTimestamp);
            exam.setStartTime(startTimestamp);
            exam.setEndTime(endTimestamp);
            exam.setTime(TimeUtils.parseTime(distanceTime));
        }else {
            exam.setStartTime(oldExam.getStartTime());
            exam.setEndTime(oldExam.getEndTime());
            exam.setTime(oldExam.getTime());
        }
        //判断使用新旧题量
        if (quantity != null) {
            exam.setQuantity(quantity);
        } else {
            exam.setQuantity(oldExam.getQuantity());
        }
        exam.setQuestion(toJsonQuestionIds);
        return examMapper.createExam(exam);
    }

    @Override
    public boolean updateExam(Exam exam) {
        if (exam.getStartTime()!=null && exam.getEndTime()!=null){
            String distanceTime = TimeUtils.getDistanceTime(exam.getStartTime(), exam.getEndTime());
            Time time = TimeUtils.parseTime(distanceTime);
            exam.setTime(time);
        }
        return examMapper.updateExam(exam);
    }

    @Override
    public boolean deleteExamById(Integer id) {
        if (id!=null){
            return examMapper.deleteExamById(id);
        }
        return false;
    }

}
