package com.allweb.questions.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.allweb.questions.entities.Course;
import com.allweb.questions.entities.Question;

import java.util.List;

public interface ICourseService extends IService<Course> {

    Page<Course> getCoursePage(Long currentPage, Long pageSize, Course course);

    List<Course> getByCourseId(Long teacherId);
}
