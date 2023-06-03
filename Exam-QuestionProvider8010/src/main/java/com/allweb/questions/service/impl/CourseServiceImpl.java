package com.allweb.questions.service.impl;

import com.allweb.questions.mapper.CourseMapper;
import com.allweb.questions.mapper.TeaCourseMapper;
import com.allweb.questions.service.ICourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.allweb.questions.entities.Course;
import com.allweb.questions.entities.TeaCourse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private TeaCourseServiceImpl teaCourseService;
    @Override
    public Page<Course> getCoursePage(Long currentPage, Long pageSize, Course course) {
        LambdaUpdateWrapper<Course> wrapper=new LambdaUpdateWrapper<>();
        wrapper.like(course.getId()!=null,Course::getId,course.getId())
                .like(course.getName()!=null,Course::getName,course.getName());
        Page<Course> coursePage = new Page<>(currentPage, pageSize);
        Page<Course> page = courseMapper.selectPage(coursePage, wrapper);
        return page;
    }

    @Override
    public List<Course> getByCourseId(Long teacherId) {
        QueryWrapper<TeaCourse> wrapper=new QueryWrapper<>();
        wrapper.select("course_id").eq("teacher_id",teacherId);
        List<String> ids = teaCourseService.list(wrapper).stream().map(TeaCourse::getCourseId).collect(Collectors.toList());
        List<Course> courses = courseMapper.selectBatchIds(ids);
        return courses;
    }
}
