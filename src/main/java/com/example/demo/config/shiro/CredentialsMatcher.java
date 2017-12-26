package com.example.demo.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Created by zonzie on 2017/12/20.
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;
//        获取用户输入的密码
        String inPassword = new String(uToken.getPassword());
//        获取数据库中的密码
        String dbPassword= (String) info.getCredentials();
//        密码比对
        return this.equals(inPassword,dbPassword);
//        return true;
    }
}
