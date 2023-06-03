package com.coe.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.coe.entities.Answer;
import com.coe.entities.Exam;
import com.coe.entities.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExamUtils {
    //分页容量
    public static final int EXAM_PAGESIZE=5;
    //分页导航栏数
    public static final int EXAM_NAVIGATEPAGES=7;
    /**
     * 将Exam中的json类型转为List<Integer>
     * @param exam 需要将Exam中的String类型的question（json形态）转为Map类型
     * @return List<Integer> 下标：试卷题目号  Integer：题目id
     */
    public static List<Integer> getQuestions(Exam exam){
        String questions = exam.getQuestion();
        JSONObject jsonObject = JSONUtil.parseObj(questions);
        Integer quantity = exam.getQuantity();
        List<Integer> list = new ArrayList<>();
        for (int i=1;i<=quantity;i++){
            String questionId = jsonObject.getStr(String.valueOf(i));
            if (questionId!=null){
                list.add(Integer.valueOf(questionId));
            }else {
                break;
            }
        }
        if (list.size()==quantity){
            return list;
        }
        return null;
    }

    /**
     * 获取完整试卷/将题目放入试卷中
     * @param questionList List<Question>型题目组，List下标为试卷题目号
     * @param exam 试卷
     * @return 将List<Question>型数据转换成Map<String,Question>后，放入试卷实体中
     */
    public static Exam getCompleteExam(List<Question> questionList,Exam exam){
        if (questionList.size()!=exam.getQuantity()){
            return null;
        }else {

//            for (Question question : questionList) {
//                System.out.print(String.valueOf(question.getId())+",");
//            }
            Map<String, Question> questionMap = new HashMap<>();
            for(int i=1;i<=questionList.size();i++){
                questionMap.put(String.valueOf(i),questionList.get(i-1));
//                System.out.println(questionMap.get(String.valueOf(i)));
            }
            for(int i=1;i<=questionList.size();i++){
                System.out.println(questionMap.get(String.valueOf(i)));
            }
            exam.setQuestionMap(questionMap);
            return exam;
        }
    }

    /**
     * 获取Json型Map<String,String>问题id组
     * @param questionIds List<String> 问题id组
     * @return 将List<String>转为Map<String,Integer>（<试卷题号，题目id>），然后再转为Json类型
     */
    public static String toJsonQuestionIds(List<Integer> questionIds){
        Map<String, Integer> map = new HashMap<>();
        for(int i=1;i<=questionIds.size();i++){
            Integer questionId = questionIds.get(i - 1);
            map.put(String.valueOf(i),questionId);
        }
        return JSONUtil.toJsonStr(map);
    }

    public static Integer getSimpleUUID(){
        String simpleUUID = IdUtil.simpleUUID();
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(simpleUUID);
        StringBuilder stringBuilder = new StringBuilder();
        int i=0;
        while (matcher.find()){
            stringBuilder.append(matcher.group());
            i++;
            if (i==6){
                break;
            }
        }
        return Integer.valueOf(String.valueOf(stringBuilder));
    }

    public static Map<String,String> getStuAnswer(String str,Integer quantity){
        if (str!=null && !"".equals(str)){
            return null;
        }else {
            return null;
        }
    }
}
