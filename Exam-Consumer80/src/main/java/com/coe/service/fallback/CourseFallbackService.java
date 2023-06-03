package com.coe.service.fallback;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coe.entities.Course;
import com.coe.service.CourseService;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CourseFallbackService implements CourseService {
    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public Page<Course> getPage(Long currentPage, Long pageSize, Course course) {
        return null;
    }

    @Override
    public Course getByIdCourse(String id) {
        return null;
    }

    @Override
    public Boolean save(Course course) {
        return null;
    }

    @Override
    public Boolean update(Course course) {
        return null;
    }

    @Override
    public Boolean deleteCourse(String id) {
        return null;
    }

    @Override
    public List<Course> getByTeacherId(Long teacherId) {
        return null;
    }
}
