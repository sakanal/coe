package com.coe.controller.Course;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coe.entities.CommonResult;
import com.coe.entities.Course;
import com.coe.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    @GetMapping
    public ModelAndView view(){
        return new ModelAndView("/ExamTch/Course/course");
    }

    @GetMapping("/getAll")
    public List<Course> getAll(){
        return courseService.getAll();
    }

    @GetMapping("/getPage/{currentPage}/{pageSize}")
    public CommonResult<Page> getAll(@PathVariable("currentPage")Long currentPage, @PathVariable("pageSize")Long pageSize, Course course){
        log.info(course.toString());
        Page<Course> page = courseService.getPage(currentPage, pageSize, course);
        if (page!=null){
            CommonResult<Page> pageCommonResult = new CommonResult<>(200,"查询成功");
            pageCommonResult.setData(page);
            return pageCommonResult;
        }
        return new CommonResult<>(500,"查询失败");
    }

    @GetMapping("/getById/{id}")
    public CommonResult<Course> getByIdCourse(@PathVariable("id") String id){
        Course course = courseService.getByIdCourse(id);
        if (course!=null){
            CommonResult<Course> commonResult = new CommonResult<>(200, "查询成功");
            commonResult.setData(course);
            return commonResult;
        }
        return new CommonResult<>(500,"查询失败");
    }

    @PostMapping("/save")
    public CommonResult<Course> save(@RequestBody Course course){
        System.out.println(course);
        Boolean save = courseService.save(course);
        if (save){
            return new CommonResult<>(200,"保存成功");
        }
        return new CommonResult<>(500,"保存失败");
    }


    @PutMapping("/update")
    public CommonResult<Course> update(@RequestBody Course course){
        Boolean success = courseService.update(course);
        if (success){
            return new CommonResult<>(200,"修改成功");
        }
        return new CommonResult<>(500,"修改失败");
    }

    @DeleteMapping("/deleteById/{id}")
    public CommonResult<Course> deleteCourse(@PathVariable("id") String id){
        Boolean success = courseService.deleteCourse(id);

        if (success){
            return new CommonResult<>(200,"删除成功");
        }
        return new CommonResult<>(500,"删除失败");
    }

    @GetMapping("/getCourse")
    public List<Course> getByTeacherId(HttpSession session){
        Long teacherId =(Long) session.getAttribute("id");
        return courseService.getByTeacherId(teacherId);
    }
}
