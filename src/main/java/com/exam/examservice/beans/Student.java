package com.exam.examservice.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * student
 * @author 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student implements Serializable {
    private String id;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 姓名
     */
    private String nickName;

    private String classId;


    private String age;

    private String gender;

    private String password;

    /**
     * 用户类型
     */

    private String className;

    private String role;




}