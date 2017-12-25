package com.example.demo.service;

import com.example.demo.DO.User;

/**
 * Created by zonzie on 2017/12/21.
 */
public interface UserService {
    User findUserByUsername(String username);
}
