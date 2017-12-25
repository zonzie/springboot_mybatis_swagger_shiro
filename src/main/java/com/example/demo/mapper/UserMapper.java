package com.example.demo.mapper;

import com.example.demo.DO.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zonzie on 2017/12/21.
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);
}
