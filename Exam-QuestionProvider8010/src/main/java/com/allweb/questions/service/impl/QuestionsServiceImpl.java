package com.allweb.questions.service.impl;


import cn.hutool.core.util.StrUtil;
import com.allweb.questions.mapper.CourseMapper;
import com.allweb.questions.mapper.QuestionsMapper;
import com.allweb.questions.service.IQuestionsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.allweb.questions.entities.Answer;
import com.allweb.questions.entities.Question;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author ouyang
 */
@Service
public class QuestionsServiceImpl extends ServiceImpl<QuestionsMapper, Question> implements IQuestionsService {

    @Resource
    private QuestionsMapper questionsMapper;

    @Resource
    private CourseMapper courseMapper;
    @Override
    public IPage<Question> getQuestionPage(Long currentPage, Long pageSize, Question question) {
        LambdaUpdateWrapper<Question> wrapper=new LambdaUpdateWrapper<>();
        System.out.println("courseId:"+question.getCourseId());
        wrapper.like(StrUtil.isNotEmpty(question.getQuestion()),Question::getQuestion,question.getQuestion())
                .like(StrUtil.isNotEmpty(question.getCourseId()),Question::getCourseId,question.getCourseId())
                .eq(question.getId()!=null,Question::getId,question.getId())
                .like(question.getTeacherId()!=null,Question::getTeacherId,question.getTeacherId());
        IPage<Question> iPage=new Page<>(currentPage,pageSize);
        IPage<Question> iPage1 = questionsMapper.selectPage(iPage, wrapper);
        return iPage1;
    }


    @Override
    public Boolean saveEntity(Question question) {
        if (StrUtil.isEmpty(question.getAnalysis())){
            question.setAnalysis("该题暂无解析");
        }
        question.setRealAnswer(question.getAnswerList().get(Integer.parseInt(question.getRealAnswer())-1).getAnswer());
        Map<String, Answer> answersMap = new HashMap<>();
        int a=65;
        for (Answer answer : question.getAnswerList()) {
//            String a1 = answer.getAnswer();
            char key=(char) a++;
            answer.setId(String.valueOf(key));
            answersMap.put(String.valueOf(key),answer);
        }
        question.setAnswersMap(answersMap);
        question.MapToStr();
        return this.save(question);
    }

    @Override
    public Question getEntityById(Long id) {
        Question question = this.getById(id);
        if (question!=null){
            question.StrToMap();
            Map<String, Answer> answersMap = question.getAnswersMap();
            String realAnswer = question.getRealAnswer();
            List<Answer> answerList = new ArrayList<>();
            Set<String> keySet = answersMap.keySet();
            int i=1;
            for (String key : keySet) {
                answersMap.get(key).setId(String.valueOf(i) );
                answerList.add(answersMap.get(key));
                if (answersMap.get(key).getAnswer().equals(realAnswer)){
                    question.setRealAnswer(String.valueOf(i));
                }
                i++;
            }
            question.setAnswerList(answerList);
            question.setAnswersMap(null);
            question.setAnswers(null);
            return question;
        }
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        return this.removeById(id);
    }

    @Override
    public List<Question> getBatchByIds(List<Integer> ids) {
        String str = StrUtil.join(",", ids);
        List<Question> questions = this.query().in("id",ids).last("ORDER BY FIELD(id,"+str+")").list();
        for (Question question : questions) {
            question.StrToMap();
        }
        return questions;
    }

    @Override
    public void addCourse(List<Question> questions){
        for (Question question : questions) {
            question.setCourse(courseMapper.selectById(question.getCourseId()));
        }
    }

    @Override
    public List<Integer> getRand(String courseId, Integer number) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.select("id").eq("course_id",courseId).last("order by rand() limit "+number);
        List<Question> list =this.list(wrapper);
        if (list.size()<number){
            return null;
        }
        List<Integer> ids=new ArrayList<>();
        for (Question question : list) {
            ids.add(question.getId());
        }
        return ids;
    }

    @Override
    public Boolean updateEntity(Question question) {
        if (question.getRealAnswer().length()==1){
            int realOption = Integer.parseInt(question.getRealAnswer());
            if (realOption <9&& realOption >0){
                question.setRealAnswer(question.getAnswerList().get(realOption -1).getAnswer());
            } else {
                question.setRealAnswer(null);
            }
        } else {
            question.setRealAnswer(null);
        }
        return this.updateById(question);
    }


}
