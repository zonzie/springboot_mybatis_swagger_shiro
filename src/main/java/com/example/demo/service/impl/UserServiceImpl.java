package com.example.demo.service.impl;

import com.example.demo.DO.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zonzie on 2017/12/21.
 */
@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;


    @Override
    public User findUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
