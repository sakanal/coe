package com.coe.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coe.entities.Exam;
import com.coe.entities.Question;
import com.coe.entities.Score;
import com.coe.mapper.ExamStuMapper;
import com.coe.service.ExamStuService;
import com.coe.utils.ExamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ExamStuServiceImpl implements ExamStuService {

    @Resource
    private ExamStuMapper examStuMapper;

    //计算成绩
    @Override
    public Score correctExam(Map<String,String> map,
                               Exam exam,
                               Long studentId,
                               Integer realQuantity,
                               String answerString) {

        log.info(String.valueOf(exam));
        Map<String, Question> questionMap = exam.getQuestionMap();

        log.info("试卷题数-->"+exam.getQuantity());
        float questionScore=100F/exam.getQuantity();
        log.info("每题得分-->"+questionScore);
        int rightSum=0;
        for (int i=1;i<=map.size();i++){
            Question question = questionMap.get(String.valueOf(i));
            String realAnswer = question.getRealAnswer();
            String answer = map.get(String.valueOf(i));

            log.info("realAnswer-->"+realAnswer);
            log.info("answer-->"+answer);
            if (Objects.equals(realAnswer, answer)){
                log.info("第"+i+"题选择正确");
                rightSum++;
            }
        }
        log.info("正确的题数-->"+rightSum);
        log.info("最终得分-->"+rightSum*questionScore);
//        return rightSum*questionScore;

        //随机化
        Integer scoreId = ExamUtils.getSimpleUUID();
        while (examStuMapper.countScoreNum(scoreId, null, null, null, null)!=0){
            scoreId = ExamUtils.getSimpleUUID();
        }
        Score score = new Score(scoreId, studentId, exam.getId(), exam.getCourseId(), exam.getClazzId(), Math.round(rightSum * questionScore), exam.getQuantity(), answerString, map);
        if (examStuMapper.insertScore(score)){
            return score;
        }else {
            return null;
        }

    }

    //判断表中是否有成绩
    @Override
    public Integer countScoreNum(Long studentId,
                                 Integer examId,
                                 String courseId,
                                 Integer clazzId) {
        Integer countScoreNum = examStuMapper.countScoreNum(null, studentId, examId, courseId, clazzId);
        log.info("次数-->"+countScoreNum);
        return countScoreNum;
    }

    //获取成绩的基本信息--不包括学生的实际答案
    @Override
    public List<Score> getScore(Long studentId, Integer examId, String courseId, Integer clazzId) {
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
        List<Score> score = examStuMapper.getScore(studentId, examId, courseId, clazzId);
        if (score.size()>0){
            return score;
        }else {
            return null;
        }

    }

    //获取成绩表的完整信息--包括学生答案
    @Override
    public Score getAnswers(Long studentId, Integer examId) {
        Score score = examStuMapper.getAnswers(studentId, examId);
        if (score!=null){
            Integer quantity = score.getQuantity();
            String answerString = score.getAnswerString();
            Map<String, String> answerMap = new HashMap<>();
            JSONObject jsonObject = JSONUtil.parseObj(answerString);
            for (int i=1;i<=quantity;i++){
                String answerValue = jsonObject.getStr(String.valueOf(i));
                answerMap.put(String.valueOf(i),answerValue);
            }
            score.setAnswerMap(answerMap);

            return score;
        }else {
            return null;
        }
    }


}
