package com.test.demo.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.test.demo.bean.Student;
import com.test.demo.dao.StudentMapper;
import com.test.demo.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: luofang
 * @create: 2019-08-27 14:24
 **/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
