package com.coe.service.fallback;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coe.entities.Question;
import com.coe.service.QuestionService;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class QuestionFallbackService implements QuestionService {
    @Override
    public Page<Question> getPage(Long currentPage, Long pageSize, Question question) {
        return null;
    }

    @Override
    public List<Question> getBatchByIds(List<Integer> ids) {
        return null;
    }

    @Override
    public Boolean save(Question question) {
        return null;
    }

    @Override
    public Question getById(Long id) {
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        return null;
    }

    @Override
    public List<Integer> getRand(String courseId, Integer number) {
        return null;
    }
    @Override
    public Boolean updateById(Question question) {
        return null;
    }

}
