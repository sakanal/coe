package com.coe.springcloud.dao;

import com.coe.entities.Clazz;
import com.coe.entities.Teacher;
import com.coe.springcloud.pojo.QueryClazz;
import com.coe.springcloud.pojo.TeaClazz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClazzDao {

    public boolean delelebyid(@Param("id") int id);


    public Clazz getClazzById(@Param("id")Integer id);

    public Integer getClazzTeaNumber(@Param("id")Integer id );

    public boolean updateClazzById(@Param("tnum") Integer tnum,@Param("snum") Integer snum,@Param("id") Integer id);

    public List<Integer> getAllClazzId();

    public List<QueryClazz> getClaInfo();

    public boolean addClazz(Clazz clazz);
    public boolean addTeaClazz(TeaClazz teaClazz);

}
