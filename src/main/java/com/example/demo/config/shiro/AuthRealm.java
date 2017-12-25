package com.example.demo.config.shiro;


import com.example.demo.DO.Module;
import com.example.demo.DO.Role;
import com.example.demo.DO.User;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zonzie on 2017/12/20.
 */
public class AuthRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;
//    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        获取session中的用户
        User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();
        List<String> permissions = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if(roles.size() > 0) {
            for(Role role: roles) {
                Set<Module> modules = role.getModules();
                if(modules.size() > 0) {
                    for(Module module : modules) {
                        permissions.add(module.getMname());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        将权限放入到shiro
        info.addStringPermissions(permissions);
        return info;
    }

//    认证,登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //        获取用户输入的token
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;
        String username = uToken.getUsername();
        User user = userService.findUserByUsername(username);
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }
}
