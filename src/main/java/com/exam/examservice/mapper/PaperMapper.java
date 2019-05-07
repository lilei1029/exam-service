package com.exam.examservice.mapper;

import com.exam.examservice.beans.Paper;
import com.exam.examservice.beans.Question;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


@Mapper
public interface PaperMapper {
    List<Paper> selectPage(@Param("searchName") String searchName, @Param("subjectId") String subjectId);

    @Select("select * from paper where subject_id=#{subjectId} limit #{count},1")
    Paper selectLimit(@Param("subjectId")String subjectId ,@Param("count") int count);

    @Select("select count(*) from paper ")
    int selectCount();

    int insert(Paper paper);

    Paper selectByName(@Param("paperName") String paperName);

    @Select("select * from paper where id=#{id}")
    Paper selectById(String id);

    @Select("select count(*) from paper where subject_id=#{subjectId} ")
    int selectSubjectCount(String subjectId);


    int updateById(Paper paper);


    int deleteByIds(String[] ids);
}