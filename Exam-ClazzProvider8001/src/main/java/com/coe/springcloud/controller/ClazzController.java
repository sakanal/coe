package com.coe.springcloud.controller;

import com.coe.entities.Clazz;
import com.coe.entities.Student;
import com.coe.entities.Teacher;
import com.coe.springcloud.pojo.AddStudent;
import com.coe.springcloud.pojo.QueryClazz;
import com.coe.springcloud.pojo.TeaClazz;
import com.coe.springcloud.pojo.studentinfo;
import com.coe.springcloud.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin
@RestController
public class ClazzController {
    @Autowired
    private ClazzService clazzService;
    //根据班号删除整个班级
    @GetMapping("/coe/class/delete/{id}")
//    http://localhost:8001//coe/class/delete/1921802
    public boolean delelebyid(@PathVariable("id") int id){

        return clazzService.delelebyid(id);
    }
    //根据班号查询班级信息
    @GetMapping("/coe/class/list/{id}")
    public Clazz queryClazzById(@PathVariable("id")Integer id) {
        updateClazzById(id);
        return clazzService.getClazzById(id);
    }

    //查询一个班级中有多少个老师
    @GetMapping("/coe/class/teanum/{id}")
//    http://localhost:8001/coe/class/teanum/1921804
    public Integer getClazzTeaNumber(@PathVariable("id")Integer id ){
        System.out.println(id+"班级中有"+clazzService.getClazzTeaNumber(id)+"个老师");
        return clazzService.getClazzTeaNumber(id);
    }
    //查询一个班级中有多少个学生
    @GetMapping("/coe/class/stunum/{id}")
//    http://localhost:8001/coe/class/stunum/1921804
    public Integer getClazzStuNumber(@PathVariable("id") Integer id){
        System.out.println(id+"班级中有"+clazzService.getClazzStuNumber(id)+"个学生");
        return clazzService.getClazzStuNumber(id);
    }
    //统计并修改一个班级学生、教师人数
    @GetMapping("/coe/class/upnum/{id}")
//    http://localhost:8001/coe/class/upnum/1921801
    public Boolean updateClazzById(@PathVariable("id") Integer id){
        return clazzService.updateClazzById(clazzService.getClazzTeaNumber(id),clazzService.getClazzStuNumber(id),id);
    }

    //查询所有所有班级id
    @GetMapping("/coe/class/getclaid")
//    http://localhost:8001/coe/class/getclaid
    public List<Integer> getAllClazzId(){
        List<Integer> allClazzId = clazzService.getAllClazzId();
        for (Integer id:allClazzId){
            updateClazzById(id);
        }
        return clazzService.getAllClazzId();
    }
    //查询所有所有班级的信息
    @GetMapping("/coe/class/getclassinfo")
//    http://localhost:8001/coe/class/getclassinfo
    public List<QueryClazz> getClaInfo(){
        List<Integer> allClazzId = clazzService.getAllClazzId();
        for (Integer id:allClazzId){
            updateClazzById(id);
        }
        return clazzService.getClaInfo();
    }

    @GetMapping("/coe/class/add")
//  例：  http://localhost:8001/coe/class/add?id=1921804
//    添加一个新的班级到Clazz表，老师数，学生数都是0
    public boolean addClazz(Clazz clazz){
        return clazzService.addClazz(clazz);
    }

    @GetMapping("/coe/class/addteacla")
//    添加一个新的记录到TeaClazz表
//    http://localhost:8001/coe/class/addteacla?clazz_id=1921804&teacher_id=1
    public boolean addTeaClazz(TeaClazz teaClazz){
        return clazzService.addTeaClazz(teaClazz);
    }

    //根据学生id删除学生
    @GetMapping("/coe/student/delete/{id}")
    public boolean DeleteStubyId(@PathVariable("id") Integer id){
        return clazzService.DeleteStubyId(id);
    }
    //增加一个学生信息
    //    http://localhost:8001/coe/student/add?id=6&password=123&name=lihuada&clazz_id=1921804
    @GetMapping("/coe/student/add")
    public boolean addStudent(AddStudent student){
        return clazzService.addStudent(student);
    }

    //查询所有学生
    @GetMapping("/coe/getstudent")
//    http://localhost:8001/coe/getstudent
    public List<studentinfo> getAllstudent(){
        return clazzService.getAllstudent();
    }

    @GetMapping("/coe/class/tid/{id}")
    //通过教师id获取所教的班级号
//    http://localhost:8001/coe/class/tid/100001
    public List<TeaClazz> getclassByteacherid(@PathVariable("id") Long id) {
        return clazzService.getclassByteacherid(id);
    }
    @GetMapping("/coe/getteacherinfo")
    public List<Teacher> getteacherinfo(){
        return clazzService.getteacherinfo();
    }
}
