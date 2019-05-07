package com.exam.examservice.mapper;

import com.exam.examservice.beans.ManyQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ManyQuestionMapper {
    List<ManyQuestion> selectPage(@Param("searchName") String searchName, @Param("subjectId") String subjectId);


    @Select("select count(*) from many_question ")
    int selectCount();

    int insert(ManyQuestion manyQuestion);

    ManyQuestion selectByName(@Param("questionName") String questionName);

    @Select("select * from many_question where id=#{id}")
    ManyQuestion selectById(String id);


    int updateById(ManyQuestion manyQuestion);

    @Select("select count(*) from many_question where subject_id=#{subjectId} ")
    int selectSubjectCount(String subjectId);

    int deleteByIds(String[] ids);

    @Select("SELECT * FROM many_question where subject_id=#{subjectId} LIMIT #{lineQuestion}, #{mNumber}")
    List<ManyQuestion> selectLimitCount(@Param("lineQuestion") Integer lineQuestion,@Param("mNumber") Integer mNumber
            ,@Param("subjectId")String subjectId);
}