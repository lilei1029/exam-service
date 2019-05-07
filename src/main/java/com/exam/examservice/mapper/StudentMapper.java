package com.exam.examservice.mapper;

import com.exam.examservice.beans.Class;
import com.exam.examservice.beans.Student;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Delete({
        "delete from student",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    @Select("select * from student where class_id=#{classId}")
    List<Student>findByClassId(String classId);

    @Select("select * from student " +
            "where student_id=#{userName} and role=#{role} and password=#{password}")
    Student findByUserName(@Param(value = "userName") String userName, @Param(value = "password") String password
            , @Param(value = "role") String role);

    @Select("select * from student where student_id=#{studentId}")
    Student findByStudentId(String studentId);

    List<Student> selectPage(@Param("searchName") String searchName,@Param("classId") String classId);

    @Select("select count(*) from student ")
    int selectCount();

    @Select("select * from student where id=#{id}")
    Student findById(String id);

    @Insert({
            "insert into student (id,student_id,nick_name,class_id,age,gender,password,role)",
            "values (#{id},#{studentId},#{nickName},#{classId},#{age},#{gender},#{password},#{role})"
    })
    int insert(Student record);


    int  updateById(Student student);

    int deleteByIds(String[] ids);



}