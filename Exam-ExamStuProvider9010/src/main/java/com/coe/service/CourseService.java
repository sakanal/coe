package com.coe.service;

import com.coe.entities.Course;
import com.coe.service.fallback.CourseFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "QuestionProvider",fallback = CourseFallbackService.class)
public interface CourseService {


    /**
     * 使用id获取课程
     * @param id
     * @return
     */
    @GetMapping("/course/getById/{id}")
    public Course getByIdCourse(@PathVariable("id") String id);

}
