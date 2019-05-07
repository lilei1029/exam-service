package com.exam.examservice.mapper;

import com.exam.examservice.beans.Record;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author LILEI
 */
@Mapper
public interface RecordMapper {

    List<Record>selectAllPage(@Param("searchName") String searchName,@Param("searchId") String searchId);

    Record selectBySubjectId(@Param("subjectId") String subjectId,@Param("studentId")String studentId);

    @Select("select count(*) from record ")
    int selectCount();

    int insert(Record record);

    List<Record> selectByStudentId(@Param("studentId") String studentId);


    Record selectRecord(@Param("studentId") String studentId,@Param("subjectId") String subjectId,@Param("id") String id);

    int deleteByIds(String[] ids);


}