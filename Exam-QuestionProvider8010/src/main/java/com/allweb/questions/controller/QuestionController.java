package com.allweb.questions.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.allweb.questions.entities.Answer;
import com.allweb.questions.entities.Question;
import com.allweb.questions.service.IQuestionsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ouyang
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private IQuestionsService questionsService;
    @PostMapping("/getPage/{currentPage}/{pageSize}")
    public Page<Question> getPage(@PathVariable("currentPage")Long currentPage, @PathVariable("pageSize")Long pageSize, @RequestBody Question question){
        IPage<Question> questionPage = questionsService.getQuestionPage(currentPage, pageSize, question);
        if (questionPage.getPages()<currentPage){
            questionPage=questionsService.getQuestionPage(questionPage.getPages(),pageSize,question);
        }
        return (Page<Question>) questionPage;
    }

    @PostMapping("/getBatchByIds")
    public List<Question> getBatchByIds(@RequestBody List<Integer> ids){
        return questionsService.getBatchByIds(ids);
    }

    @PostMapping("/save")
    public Boolean save(@RequestBody Question question){
        return questionsService.saveEntity(question);
    }

    @GetMapping("/getById/{id}")
    public Question getById(@PathVariable("id") Long id){
        return questionsService.getEntityById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteById(@PathVariable("id") Long id){
        return questionsService.deleteById(id);
    }

    @GetMapping("/getRand/{courseId}/{number}")
    public List<Integer> getRand(@PathVariable("courseId")String courseId,@PathVariable("number") Integer number){
        return questionsService.getRand(courseId,number);
    }

    @PutMapping("/update")
    public Boolean updateById(@RequestBody Question question){
        return questionsService.updateEntity(question);
    }
}
