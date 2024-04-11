package com.example.myLibrary.dao;

import com.example.myLibrary.dto.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public User loginUser(User user) throws Exception;
}
