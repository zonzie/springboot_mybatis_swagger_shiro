package com.example.demo.controller;

import com.example.demo.DO.User;
import com.example.demo.VO.BaseResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by zonzie on 2017/12/21.
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", defaultValue = "jack", value = "用户名"),
            @ApiImplicitParam(paramType = "query", name = "password", defaultValue = "123", value = "密码"),
    })
    public BaseResponse login(String username, String password, HttpSession httpSession) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);
            User user = (User) subject.getPrincipal();
            httpSession.setAttribute("user",user);
            return new BaseResponse(123,"hello");
        } catch(Exception e) {
            return new BaseResponse(890,"error");
        }
    }
}
