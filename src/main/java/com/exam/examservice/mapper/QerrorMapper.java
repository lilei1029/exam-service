package com.exam.examservice.mapper;

import com.exam.examservice.beans.Qerror;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QerrorMapper {

    List<Qerror> selectPage(@Param("searchName") String searchName, @Param("subjectId") String subjectId);

    @Select("select count(*) from q_error ")
    int selectCount();

    Qerror selectOne(@Param("questionId") String questionId);

    @Select("select count(*) from q_error where subject_id=#{subjectId} ")
    int selectSubjectCount(String subjectId);

    int updateById(Qerror qerror);

    int insert(Qerror qerror);
}
