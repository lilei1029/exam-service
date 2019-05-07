package com.exam.examservice.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * paper
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper implements Serializable {
    private String id;

    private String subjectId;

    private String subjectName;

    private String paperName;

    private String content;

    private String score;

    private int testTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")//规定时间格式，转换时间区
    private Date createTime;

    private String createUser;


}