package com.exam.examservice.mapper;

import com.exam.examservice.beans.Class;
import com.exam.examservice.beans.Subject;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface ClassMapper {

//    @Select("select * from class")
//    List<Class>findAll();


    List<Class> selectPage(@Param("searchName") String searchName);

    @Select("select count(*) from class ")
    int selectCount();

    @Select("select id ,class_name from class where class_name=#{className,jdbcType=VARCHAR}")
   Class selectByName(String className);

    @Select("select id ,class_name  from class where id=#{id,jdbcType=VARCHAR}")
    Class selectById(String id);

    @Delete({
        "delete from class",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    int deleteByIds(String[] ids);

    @Insert({
        "insert into class (id,class_name)",
        "values (#{id},#{className})"
    })
    int insert(Class record);

    @Select("select * from class")
    List<Class> selectAll();

//    @InsertProvider(type=ClassSqlProvider.class, method="insertSelective")
//    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=String.class)
//    int insertSelective(Class record);

//    @Select({
//        "select",
//        "class.id as class_id, class.class_name as class_class_name",
//        "from class class",
//        "where class.id = #{id,jdbcType=VARCHAR}"
//    })
//    @Results({
//        @Result(column="class_id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
//        @Result(column="class_class_name", property="className", jdbcType=JdbcType.VARCHAR)
//    })
//    Class selectByPrimaryKey(String id);

//    @UpdateProvider(type=ClassSqlProvider.class, method="updateByPrimaryKeySelective")
//    int updateByPrimaryKeySelective(Class record);

   @Update("update class set id=#{id}, class_name = #{className} where id = #{id}")
    int updateById(Class record);
}