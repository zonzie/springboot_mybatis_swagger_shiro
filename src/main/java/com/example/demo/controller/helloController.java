package com.example.demo.controller;


import com.example.demo.VO.BaseResponse;
import com.example.demo.mapper.CityMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zonzie on 2017/12/12.
 */
@RestController
@RequestMapping("/demo")
//@EnableAutoConfiguration
public class helloController {

    @Resource
    private CityMapper cityMapper;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    @RequiresGuest
    String test1() {
        return "hello,test1";
    }

    @GetMapping("/findCity")
    @RequiresAuthentication
    Object findCity(@RequestParam String id) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            return cityMapper.findCityById(id);
        }
        return new BaseResponse(456,"未登录");
    }
}
