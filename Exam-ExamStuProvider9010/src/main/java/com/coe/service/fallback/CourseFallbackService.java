package com.coe.service.fallback;

import com.coe.entities.Course;
import com.coe.service.CourseService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseFallbackService implements CourseService {
    @Override
    public Course getByIdCourse(String id) {
        return null;
    }
}
