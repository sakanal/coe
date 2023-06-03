package com.allweb.questions.service.impl;

import com.allweb.questions.mapper.TeaCourseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.allweb.questions.entities.TeaCourse;
import org.springframework.stereotype.Service;

@Service
public class TeaCourseServiceImpl extends ServiceImpl<TeaCourseMapper,TeaCourse> implements IService<TeaCourse>  {
}
