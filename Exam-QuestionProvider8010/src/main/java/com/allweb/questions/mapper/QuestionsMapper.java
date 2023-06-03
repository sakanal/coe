package com.allweb.questions.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.allweb.questions.entities.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionsMapper extends BaseMapper<Question> {
}
