package com.exam.examservice.mapper;


import com.exam.examservice.beans.Number;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NumberMapper {

    Number selectById(@Param("id") String id);

    Number selectByPaperId(@Param("paperId") String paperId);

    int insert(Number number);

    int updateById(Number number);

    int updateByPaperId(Number number);

}
