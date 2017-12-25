package com.example.demo.mapper;

import com.example.demo.DO.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zonzie on 2017/12/12.
 */
@Mapper
public interface CityMapper {
    @Select("select * from city where id = #{id}")
    City findCityById(@Param("id") String id);
}
