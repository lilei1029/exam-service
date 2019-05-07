package com.exam.examservice.mapper;

import com.exam.examservice.beans.Student;
import com.exam.examservice.beans.Teacher;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author LILEI
 */
@Mapper
public interface TeacherMapper {

    List<Teacher> selectPage(@Param("searchName") String searchName);

    @Select("select count(*) from teacher ")
    int selectCount();

    @Select("select * from teacher " +
            "where teacher_id=#{userName} and role=#{role} and password=#{password}")
    Teacher findByUserName(@Param(value = "userName") String userName, @Param(value = "password") String password
            , @Param(value = "role") String role);

    int insert(Teacher record);

    Teacher findByTeacherId(String teacherId);

    Teacher selectById(String id);

    int updateById(Teacher teacher);

    int deleteByIds(String[] ids);

}