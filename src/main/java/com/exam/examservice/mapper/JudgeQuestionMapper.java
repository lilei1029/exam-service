package com.exam.examservice.mapper;

import com.exam.examservice.beans.JudgeQuestion;
import com.exam.examservice.beans.Question;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface JudgeQuestionMapper {
    List<JudgeQuestion> selectPage(@Param("searchName") String searchName, @Param("subjectId") String subjectId);


    @Select("select count(*) from judge_question ")
    int selectCount();

    int insert(JudgeQuestion question);

    JudgeQuestion selectByName(@Param("questionName") String questionName);

    @Select("select * from judge_question where id=#{id}")
    JudgeQuestion selectById(String id);

    @Select("select count(*) from judge_question where subject_id=#{subjectId} ")
    int selectSubjectCount(String subjectId);

    int updateById(JudgeQuestion question);


    int deleteByIds(String[] ids);

    @Select("SELECT * FROM judge_question where subject_id=#{subjectId} LIMIT #{lineQuestion}, #{jNumber}")
    List<JudgeQuestion> selectLimitCount(@Param("lineQuestion") Integer lineQuestion,@Param("jNumber") Integer jNumber
            ,@Param("subjectId")String subjectId);
}