package com.coe.controller.Clazz;

import com.coe.entities.Clazz;
import com.coe.entities.CommonResult;
import com.coe.entities.Student;
import com.coe.pojo.QueryClazz;
import com.coe.pojo.TeaClazz;
import com.coe.service.ClazzService;
import com.coe.service.StudentService;
import com.netflix.ribbon.proxy.annotation.Http;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ClazzRestController {
    @Resource
    private ClazzService clazzService;
    @Resource
    private StudentService studentService;

    @GetMapping("/coe/class/getAllClazzInfo")
    public CommonResult<List<QueryClazz>> getAllClazzInfo(){
        log.info("开始运行客户端中的ClazzController方法，该方法会获取所有的班级信息");
        List<QueryClazz> claInfo = clazzService.getClaInfo();
        for (QueryClazz queryClazz : claInfo) {
            log.info(String.valueOf(queryClazz));
        }
        if (claInfo!=null){
            return new CommonResult<>(200,"查询所有班级信息成功",claInfo);
        }else {
            return new CommonResult<>(444,"暂无班级信息");
        }
    }


    @GetMapping("/coe/class/getMyClazzList")
    public CommonResult<List<Clazz>> getMyClazzList(HttpSession httpSession){
        log.info("开始运行客户端中的getMyClazzList方法，该方法会获取已经登录教师所教授的所有的班级信息");
        Long teacherId =(Long) httpSession.getAttribute("id");
//        Long teacherId=Long.parseLong(id);
        List<TeaClazz> teaClazzes = clazzService.getclassByteacherid(teacherId);
        List<Clazz> clazzList = new ArrayList<>();
        for (TeaClazz teaClazz : teaClazzes) {
            Integer clazzId = teaClazz.getClazzId();
            Clazz clazz = clazzService.queryClazzById(clazzId);
            clazzList.add(clazz);
        }
        if (clazzList.size()>0){
            return new CommonResult<>(200,"查询教师所带班级成功",clazzList);
        }else {
            return new CommonResult<>(444,"该教师没有教授的班级");
        }
    }


    @GetMapping("/coe/class/getClazzInfo")
    public CommonResult<Clazz> getClazzInfo(HttpSession httpSession){
        log.info("开始运行客户端中的getClazzInfo方法，该方法会根据班级id获取班级信息，包括教师和学生的详细信息");
        Integer clazzId =(Integer) httpSession.getAttribute("clazzId");
        log.info(String.valueOf(clazzId));

        Clazz clazz = clazzService.queryClazzById(clazzId);

        if (clazz!=null){
            return new CommonResult<>(200,"获取班级详细信息成功",clazz);
        }else {
            return new CommonResult<>(444,"获取班级详细信息失败");
        }

    }
    @GetMapping("/coe/class/getNotInClassStudentList")
    public CommonResult<List<Student>> getNotInClassStudentList(){
        List<Student> studentAll = studentService.getStudentAll();
        for (Student student : studentAll) {
            log.info(String.valueOf(student));
        }
        if (studentAll!=null && studentAll.size()>0){
            return new CommonResult<>(200,"获取学生列表成功",studentAll);
        }else {
            return new CommonResult<>(444,"暂无未加入班级的学生");
        }
    }

    @PostMapping("/coe/class/addStudentToClass")
    public CommonResult<Boolean> addStudentToClass(@RequestParam("clazzId")Integer clazzId,
                                                   @RequestParam("studentId")Long studentId){
        Student student = new Student();
        student.setClazzId(clazzId);
        student.setId(studentId);
        return studentService.updateClass(student);
    }


    @GetMapping("/coe/class/tid")
    //通过教师id获取所教的班级号
//    http://localhost:8001/coe/class/tid/100001
    public List<TeaClazz> getclassByteacherid(HttpSession httpSession){
        Long teacherId =(Long) httpSession.getAttribute("id");
//        Long teacherId=Long.parseLong(id);
        return clazzService.getclassByteacherid(teacherId);
    }
//
//    @GetMapping("/coe/class/getClazzId")
//    public Integer getClazzId(HttpSession httpSession){
//        return (Integer) httpSession.getAttribute("clazzId");
//    }

}
