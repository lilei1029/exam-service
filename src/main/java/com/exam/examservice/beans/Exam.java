package com.exam.examservice.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam implements Serializable{

   private String id;

    private String subjectId;

    private String subjectName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")//规定时间格式，转换时间区
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")//规定时间格式，转换时间区
    private Date endTime;

 /**
  * 0 表示已结束考试   1  表示正在进行
  */
 private String state;

 private String stateValue;

}
