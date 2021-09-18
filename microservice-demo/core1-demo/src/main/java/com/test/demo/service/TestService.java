package com.test.demo.service;

import com.test.demo.bean.User;



public interface TestService {

    String queryUserByUserId(String userId);

    String queryUserListByCondition(String msg);

    String queryUserList();

    String addUser(User user);

    String updateUser(User user);

    String deleteUser(String userId);
}
