package com.allweb.questions.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.allweb.questions.entities.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
