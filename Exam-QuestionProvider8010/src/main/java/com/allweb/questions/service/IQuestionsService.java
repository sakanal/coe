package com.allweb.questions.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.allweb.questions.entities.Question;

import java.util.List;


public interface IQuestionsService extends IService<Question> {
    IPage<Question> getQuestionPage(Long currentPage, Long pageSize, Question question);

    Boolean saveEntity(Question question);

    Question getEntityById(Long id);

    Boolean deleteById(Long id);

    List<Question> getBatchByIds(List<Integer> ids);

    void addCourse(List<Question> questions);

    List<Integer> getRand(String courseId, Integer number);

    Boolean updateEntity(Question question);
}
