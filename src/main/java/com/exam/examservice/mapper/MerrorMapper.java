package com.exam.examservice.mapper;

import com.exam.examservice.beans.Merror;
import com.exam.examservice.beans.Qerror;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MerrorMapper {

    List<Merror> selectPage(@Param("searchName") String searchName, @Param("subjectId") String subjectId);

    @Select("select count(*) from m_error ")
    int selectCount();

    Merror selectOne(@Param("questionId")String questionId);

    @Select("select count(*) from m_error where subject_id=#{subjectId} ")
    int selectSubjectCount(String subjectId);

    int updateById(Merror merror);

    int insert(Merror merror);
}
