package com.coe.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
    private Integer id;//试卷id
    private Long teacherId;//教师id
    private Teacher teacher;

    private Integer clazzId;//班级id
    private Clazz clazz;

    private String courseId;//课程id
    private Course course;//课程

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp startTime;//考试开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp endTime;//考试结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Time time;//考试时间
    private Integer quantity;//题目量
    //存储形式:Map<String,Integer>：第一个String为试卷题号，第二个String为题目id
    private String question;//json类型问题组id，获取数据库中存在的json类型数据
    private Map<String,Question> questionMap;//map类型题目组，<题号，question>
    private Integer state;//考试状态（0：未开始，1：已开始，2：已结束）
}
