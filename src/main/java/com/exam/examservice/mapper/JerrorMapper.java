package com.exam.examservice.mapper;

import com.exam.examservice.beans.Jerror;
import com.exam.examservice.beans.Qerror;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface JerrorMapper {

    List<Jerror> selectPage(@Param("searchName") String searchName, @Param("subjectId") String subjectId);

    @Select("select count(*) from j_error ")
    int selectCount();

    Jerror selectOne(@Param("questionId")String questionId);

    @Select("select count(*) from j_error where subject_id=#{subjectId} ")
    int selectSubjectCount(String subjectId);

    int updateById(Jerror jerror);

    int insert(Jerror jerror);
}
