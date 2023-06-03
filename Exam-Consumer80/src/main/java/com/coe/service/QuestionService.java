package com.coe.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coe.entities.Question;
import com.coe.service.fallback.QuestionFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务名：CLOUD-QUESTIONS-SERVER
 * 包含功能：问题的增删改查、课程的增删改查
 */
@Component
@FeignClient(value = "QuestionProvider",fallback = QuestionFallbackService.class)
public interface QuestionService {
    /**
     * 题目分页查询
     * @param currentPage 请求数据当前页
     * @param pageSize  页大小
     * @param question 查询条件参数
     * @return
     */
    @PostMapping("/question/getPage/{currentPage}/{pageSize}")
    public Page<Question> getPage(@PathVariable("currentPage")Long currentPage, @PathVariable("pageSize")Long pageSize, Question question);

    /**
     * 根据IdList查询问题
     * @param ids  要查询的问题id集合
     * @return
     */
    @PostMapping("/question/getBatchByIds")
    public List<Question> getBatchByIds(List<Integer> ids);

    /**
     * 保存问题
     * @param question
     * @return
     */
    @PostMapping("/question/save")
    public Boolean save(@RequestBody Question question);

    /**
     * 根据Id获取单个问题
     * @param id
     * @return
     */
    @GetMapping("/question/getById/{id}")
    public Question getById(@PathVariable("id") Long id);

    /**
     * 根据id删除问题
     * @param id
     * @return
     */
    @DeleteMapping("/question/deleteById/{id}")
    public Boolean deleteById(@PathVariable("id") Long id);

    /**
     * 随机获取number条数据
     * @param courseId
     * @param number
     * @return
     */
    @GetMapping("/question/getRand/{courseId}/{number}")
    public List<Integer> getRand(@PathVariable("courseId")String courseId,@PathVariable("number") Integer number);

    /**
     * 根据id更新问题
     * @param question
     * @return
     */
    @PutMapping("/question/update")
    public Boolean updateById(@RequestBody Question question);


}
