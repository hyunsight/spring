package com.example.myLibrary.service;

import com.example.myLibrary.dao.UserDao;
import com.example.myLibrary.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User loginUser(User user) throws Exception {
        return userDao.loginUser(user);
    }
}
