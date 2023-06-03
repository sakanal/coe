package com.allweb.questions.controller;

import cn.hutool.core.util.StrUtil;
import com.allweb.questions.service.ICourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.allweb.questions.entities.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/course")
public class CourseController {
    @Resource
    private ICourseService courseService;
    @GetMapping("/getAll")
    public List<Course> getAll(){
        return courseService.list();
    }
    @PostMapping("/getPage/{currentPage}/{pageSize}")
    public Page<Course> getPage(@PathVariable("currentPage")Long currentPage, @PathVariable("pageSize")Long pageSize,@RequestBody Course course){
        Page<Course> questionPage = courseService.getCoursePage(currentPage, pageSize,course);
        return questionPage;
    }
    @GetMapping("/getById/{id}")
    public Course getByIdCourse(@PathVariable("id") String id){
        Course course = courseService.getById(id);
        return course;
    }
    @PostMapping("/save")
    public Boolean save(@RequestBody Course course){
        if (course.getId()==null){
            QueryWrapper<Course> wrapper = new QueryWrapper<>();
            Boolean flag=true;
            String id=null;
            while (flag){
                id = StrUtil.sub(StrUtil.uuid(), 0, 7);
                wrapper.eq("id",id);
                Course one = courseService.getOne(wrapper);
                flag=(one!=null);
            }
            course.setId(id);
        }
        System.out.println(course);
        return courseService.save(course);
    }
    @PutMapping("/update")
    public Boolean update(@RequestBody Course course){
        return courseService.updateById(course);
    }
    @DeleteMapping("/deleteById/{id}")
    public Boolean deleteCourse(@PathVariable("id") String id){
        try {
            return courseService.removeById(id);
        }catch (Exception e){
            return false;
        }
    }

    @GetMapping("/getByTeacherId/{id}")
    public List<Course> getByTeacherId(@PathVariable("id") Long teacherId){
        return courseService.getByCourseId(teacherId);
    }

}
