package com.coe.controller.Score;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/Score")
@Slf4j
public class ScoreController {
    @GetMapping("/toScoreList/{clazzId}/{examId}")
    public ModelAndView toScoreList(@PathVariable("clazzId")Integer clazzId,
                                    @PathVariable("examId")Integer examId,
                                    HttpSession httpSession){
        log.info("开始跳转到班级试卷详细页面");
        httpSession.setAttribute("clazzId",clazzId);
        httpSession.setAttribute("examId",examId);
        return new ModelAndView("/ExamTch/Score/scoreList");
    }
}
