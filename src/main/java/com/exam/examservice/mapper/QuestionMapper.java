package com.exam.examservice.mapper;

import com.exam.examservice.beans.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    List<Question>selectPage(@Param("searchName") String searchName,@Param("subjectId") String subjectId);


    @Select("select count(*) from question ")
    int selectCount();

    @Select("select count(*) from question where subject_id=#{subjectId} ")
    int selectSubjectCount(String subjectId);

    int insert(Question question);

    Question selectByName(@Param("questionName") String questionName);

    @Select("select * from question where id=#{id}")
    Question selectById(String id);


    int updateById(Question question);


    int deleteByIds(String[] ids);

    @Select("SELECT * FROM question where subject_id=#{subjectId} LIMIT #{lineQuestion}, #{qNumber}")
    List<Question> selectLimitCount(@Param("lineQuestion") Integer lineQuestion,@Param("qNumber") Integer qNumber
    ,@Param("subjectId")String subjectId);

}