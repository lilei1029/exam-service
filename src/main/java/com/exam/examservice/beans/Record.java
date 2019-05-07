package com.exam.examservice.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * record
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record implements Serializable {
    private String id;

    private String paperId;

    private String paperName;

    private String studentId;

    private String studentName;

    private String subjectId;

    private String subjectName;


//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")//规定时间格式，转换时间区
    private String answerTime;

    private String qScore;

    private String mScore;

    private  String jScore;

    private String score;

    private String content;


}