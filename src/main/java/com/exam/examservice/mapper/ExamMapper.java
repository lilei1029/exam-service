package com.exam.examservice.mapper;

import com.exam.examservice.beans.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author LILEI
 */
@Mapper
public interface ExamMapper {

    List<Exam> selectPage();

    List<Exam> findEndTimeLessSysdate();


    List<Exam>selectPageByState();

    @Select("select count(*) from exam ")
    int selectCount();

    int insert(Exam exam);

    Exam selectBySubjectId(@Param("subjectId") String subjectId);

    @Select("select * from exam where id=#{id}")
    Exam selectById(String id);



    int updateById(Exam exam);


    int deleteByIds(String[] ids);
}
