package com.coe.springcloud.service;

import com.coe.entities.*;
import com.coe.springcloud.dao.ClazzDao;
import com.coe.springcloud.dao.StudentMapper;
import com.coe.springcloud.dao.TeacherMapper;
import com.coe.springcloud.pojo.AddStudent;
import com.coe.springcloud.pojo.QueryClazz;
import com.coe.springcloud.pojo.TeaClazz;
import com.coe.springcloud.pojo.studentinfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzDao clazzDao;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private StudentMapper studentMapper;
    @Override


    public Clazz getClazzById(Integer id){
        Clazz clazz = null;
        if (id!=null) {
            clazz = clazzDao.getClazzById(id);
            List<Long> teacherIds = teacherMapper.getTeachersIdByClazzId(id);
            if (teacherIds.size()!=0){
                clazz.setTeacherIds(teacherIds);
                List<Teacher> teacherList = teacherMapper.getTeachersByTeacherId(teacherIds);
                clazz.setTeacherList(teacherList);
            }

            List<Long> studentIds = studentMapper.getStudentIdsByclazzId(id);
            if (studentIds.size()!=0){
                clazz.setStudentIds(studentIds);
                List<Student> studentList = studentMapper.getStudentByStudentId(studentIds);
                Student studentByStudentId = studentMapper.getStudentById(studentIds.get(0));
                log.info(String.valueOf(studentByStudentId));
//                log.info(String.valueOf(studentList));
                clazz.setStudentList(studentList);
            }

        }
        return clazz;
    }

    @Override
    public boolean delelebyid(int id) {
        return clazzDao.delelebyid(id);
    }

    @Override
    public Integer getClazzTeaNumber(Integer id) {
        return clazzDao.getClazzTeaNumber(id);
    }

    @Override
    public Integer getClazzStuNumber(Integer id) {
        return studentMapper.getClazzStuNumber(id);
    }

    @Override
    public boolean updateClazzById(Integer tnum, Integer snum,Integer id) {
        return clazzDao.updateClazzById(tnum, snum,id);
    }

    @Override
    public List<Integer> getAllClazzId() {
        return clazzDao.getAllClazzId();
    }



    @Override
    public List<QueryClazz> getClaInfo() {

        return clazzDao.getClaInfo();
    }

    @Override
    public boolean addClazz(Clazz clazz) {
        return clazzDao.addClazz(clazz);
    }

    @Override
    public boolean addTeaClazz(TeaClazz teaClazz) {
        return clazzDao.addTeaClazz(teaClazz);
    }

    @Override
    public boolean DeleteStubyId(Integer id) {
        return studentMapper.DeleteStubyId(id);
    }

    @Override
    public boolean addStudent(AddStudent student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public List<studentinfo> getAllstudent() {
        return studentMapper.getAllstudent();

    }

    @Override
    public List<TeaClazz> getclassByteacherid(Long id) {
        return teacherMapper.getclassByteacherid(id);
    }

    @Override
    public List<Teacher> getteacherinfo(){
        return teacherMapper.getteacherinfo();
    }

}
