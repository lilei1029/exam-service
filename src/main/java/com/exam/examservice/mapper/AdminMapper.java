package com.exam.examservice.mapper;

import com.exam.examservice.beans.Admin;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AdminMapper {
    @Delete({
        "delete from admin",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    @Select("select * from admin " +
            "where user_name=#{userName} and role=#{role} and password=#{password}")
    Admin findByUserName(@Param(value = "userName") String userName, @Param(value = "password") String password, @Param(value = "role") String role);

    @Select("select * from admin where user_name=#{userName}")
    Admin selectByUserName(@Param(value = "userName") String userName);

    @Insert({
        "insert into admin (user_name, password, ",
        "role)",
        "values (#{userName,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{role,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=String.class)
    int insert(Admin record);



    @Update("update admin set user_name=#{userName},nick_name=#{nickName},gender=#{gender},telephone=#{telephone},email=#{email}," +
            "password=#{password} where id=#{id}")
    int updateById(Admin admin);

//    int updateBy
}