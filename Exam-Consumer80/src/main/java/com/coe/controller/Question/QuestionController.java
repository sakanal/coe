package com.coe.controller.Question;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coe.entities.CommonResult;
import com.coe.entities.Question;
import com.coe.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private QuestionService questionService;

    @GetMapping
    public ModelAndView getView(){
        return new ModelAndView("/ExamTch/Question/question");
    }

    @PostMapping("/save")
    public CommonResult<Question> save(@RequestBody Question question, HttpSession session){
        Long teacherId =(Long) session.getAttribute("id");
        question.setTeacherId(teacherId);
        question.getAnswerList();
        Boolean success = questionService.save(question);
        if (success){
            return new CommonResult<>(200,"保存成功");
        }
        return new CommonResult<>(500,"保存失败");
    }

    @GetMapping("/getById/{id}")
    public CommonResult<Question> getById(@PathVariable("id") Long id){
        CommonResult<Question> commonResult = new CommonResult<>();
        Question question = questionService.getById(id);
        System.out.println(question);
        if (question!=null){
            commonResult.setCode(200);
            commonResult.setData(question);
            return commonResult;
        }
        commonResult.setMessage("未找到该条记录");
        commonResult.setCode(404);
        return commonResult;
    }

    @GetMapping("/getPage/{currentPage}/{pageSize}")
    public CommonResult<Page> getPage(@PathVariable("currentPage")Long currentPage, @PathVariable("pageSize")Long pageSize, Question question){
        CommonResult<Page> commonResult = new CommonResult<>(200,null);
        Page<Question> page = questionService.getPage(currentPage, pageSize, question);
        if (page==null){
            commonResult.setCode(400);
        }
        commonResult.setData(page);
        return commonResult;
    }
    @GetMapping("/getRand/{courseId}/{number}")
    public List<Integer> getRand(@PathVariable("courseId")String courseId, @PathVariable("number") Integer number){
        return questionService.getRand(courseId,number);
    }
    @DeleteMapping("/deleteById/{id}")
    public CommonResult<Question>  deleteById(@PathVariable("id") Long id){
        Boolean success = questionService.deleteById(id);
        if (success){
            return new CommonResult<Question>(200,"删除成功");
        }
        return new CommonResult<>(500,"删除失败");
    }
    @PutMapping("/update")
    public CommonResult<Question> update(@RequestBody Question question){
        Boolean success = questionService.updateById(question);
        if (success){
            return new CommonResult<Question>(200,"修改成功");
        }
        return new CommonResult<>(500,"修改失败");
    }

    @GetMapping("/getBatch")
    public void test1(){
        List<Integer> ids=new ArrayList<>();
        ids.add(42);
        ids.add(6);
        ids.add(7);
        List<Question> batchByIds = questionService.getBatchByIds(ids);
        System.out.println(batchByIds);
    }

    public CommonResult<Question> defaultOrderInfo_TimeOutHandler(){
        return new CommonResult<>(500,"服务器异常");
    }
}
