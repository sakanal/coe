package com.allweb.questions.entities;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("question_bank")
public class Question {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**出题教师id*/
    private Long teacherId;

    /**题目题干*/
    private String question;

    /**json类型答案组，用于获取数据库中的json类型数据，同时用于将Map<String,Answer>类型数据装为String存入数据库中（JSONUtil.toJsonStr(obj)）*/
    private String answers;

    @TableField(exist = false)
    private List<Answer> answerList;

    /**map类型答案组,Map<(A/B/C/D),Answer>，将属性Answers使用JSONUtil.parseObj(answers).getStr(key)获取Answer放入该属性中*/
    @TableField(exist = false)
    private Map<String, Answer> answersMap;

    /**用于记录四个选项中的正确选项号A/B/C/D，为Map<String,Answer> answersMap中的String*/
    @TableField(value = "real_answer")
    private String realAnswer;

    /**正确答案，使用JSONUtil.toBean(realAnswer, Answer.class)将realAnswer转为Answer类型放入属性中*/
    @TableField(exist = false)
    private Answer answer;

    /**解析*/
    private String analysis;

    /**课程*/
    @TableField(exist = false)
    private Course course;


    private String courseId;

    public void MapToStr(){
        this.answers= JSONUtil.toJsonStr(this.answersMap);
    }
    public void StrToMap(){
        this.answersMap=new HashMap<>();
        JSONObject jsonObject = JSONUtil.parseObj(answers);
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            answersMap.put(key,jsonObject.get(key,Answer.class));
        }
        this.answers=null;
    }
}
