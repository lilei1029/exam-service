package com.exam.examservice.mapper;

import com.exam.examservice.beans.Class;
import com.exam.examservice.beans.Subject;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


@Mapper
public interface SubjectMapper {

    List<Subject> selectPage(@Param("searchName") String searchName);

    @Select("select count(*) from subject ")
    int selectCount();

    @Select("select id ,subject_name from subject where subject_name=#{subjectName,jdbcType=VARCHAR}")
    Subject selectByName(String subjectName);

    @Select("select id ,subject_name  from subject where id=#{id,jdbcType=VARCHAR}")
    Subject selectById(String id);

    @Select("select * from subject")
    List<Subject> selectAll();

    int deleteByIds(String[] ids);

    @Insert({
            "insert into subject (id,subject_name)",
            "values (#{id},#{subjectName})"
    })
    int insert(Subject record);


    @Update("update subject set id=#{id},subject_name = #{subjectName} where id = #{id}")
    int updateById(Subject record);
}