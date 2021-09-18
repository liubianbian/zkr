package com.test.demo.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.test.demo.bean.Student;
import com.test.demo.service.StudentService;
import com.test.demo.util.FastJsonUtil;
import com.test.demo.bean.User;
import com.test.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Value("${test.a}")
    private String applicationLocation;

    private List<User> userList;

    @Autowired
    StudentService studentService;

    @Override
    public String queryUserByUserId(String userId) {
        return FastJsonUtil.toJson(buildUserList().get(0));
    }

    @Override
    public String queryUserListByCondition(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("msg", msg);
        return FastJsonUtil.toJson(map);
    }

    @Override
    public String queryUserList() {
        log.info(applicationLocation);
        Student student = new Student();
//        student.setId("3");
        char name = (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
        student.setName(String.valueOf(name));
        student.setSex("0");
        log.info("mysql查询插入数据信息：{}", FastJsonUtil.toJson(studentService.insert(student)));
        EntityWrapper<Student> wrapper = new EntityWrapper<>();
        return FastJsonUtil.toJson(studentService.selectList(wrapper));
    }

    @Override
    public String addUser(User user) {
        if(user == null) {
            return ("添加用户不能为空");
        }
        if(StringUtils.isBlank(user.getUserId())) {
            return ("添加用户编号不能为空");
        }
        try {
            userList.add(user);
            return ("添加用户成功!");
        } catch (Exception e) {
            return ("添加用户失败");
        }
    }

    @Override
    public String updateUser(User user) {
        if(user == null) {
            return ("更新用户不能为空");
        }
        if(StringUtils.isBlank(user.getUserId())) {
            return ("修改用户编号不能为空");
        }
        String userId = user.getUserId();
        Iterator<User> it = userList.iterator();
        while(it.hasNext()) {
            User u = it.next();
            if(userId.equals(u.getUserId())) {
                userList.remove(u);
                userList.add(user);
                return ("更新用户成功!");
            }
        }
        return ("用户不存在，更新用户失败");
    }

    @Override
    public String deleteUser(String userId) {
        if(StringUtils.isBlank(userId)) {
            return ("删除用户的编号不能为空");
        }
        Iterator<User> it = userList.iterator();
        while(it.hasNext()) {
            User u = it.next();
            if(userId.equals(u.getUserId())) {
                userList.remove(u);
                return ("删除用户成功!");
            }
        }
        return ("用户不存在，删除用户失败");
    }

    @PostConstruct
    private List<User> buildUserList() {
        log.info(applicationLocation);
        userList = new ArrayList<>();
        User user;
        for(int i = 1; i <= 5; i++) {
            user = new User();
            user.setUserId("SZK00" + i);
            user.setName("张三" + i);
            user.setGender("male");
            user.setRemark("cignacmb" + i);
            userList.add(user);
        }
        return userList;
    }
}
