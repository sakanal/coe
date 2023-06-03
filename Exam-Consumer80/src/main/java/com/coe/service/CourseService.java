package com.coe.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coe.entities.Course;
import com.coe.service.fallback.CourseFallbackService;
import com.coe.service.fallback.QuestionFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Component
@FeignClient(value = "QuestionProvider",fallback = CourseFallbackService.class)
public interface CourseService {

    /**
     * 获取所有课程
     * @return
     */
    @GetMapping("/course/getAll")
    public List<Course> getAll();


    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param course
     * @return
     */
    @PostMapping("/course/getPage/{currentPage}/{pageSize}")
    public Page<Course> getPage(@PathVariable("currentPage")Long currentPage, @PathVariable("pageSize")Long pageSize, Course course);
    /**
     * 使用id获取课程
     * @param id
     * @return
     */
    @GetMapping("/course/getById/{id}")
    public Course getByIdCourse(@PathVariable("id") String id);

    /**
     * 保存课程
     * @param course
     * @return
     */
    @PostMapping("/course/save")
    public Boolean save(@RequestBody Course course);

    /**
     * 更新课程
     * @param course
     * @return
     */
    @PutMapping("/course/update")
    public Boolean update(@RequestBody Course course);

    /**
     * 根据Id删除课程
     * @param id
     * @return
     */
    @DeleteMapping("/course/deleteById/{id}")
    public Boolean deleteCourse(@PathVariable("id") String id);

    /**
     * 根据教师id获取所有课程信息
     * @param teacherId
     * @return
     */
    @GetMapping("/course/getByTeacherId/{id}")
    public List<Course> getByTeacherId(@PathVariable("id") Long teacherId);
}
