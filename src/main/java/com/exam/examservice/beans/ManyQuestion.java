package com.exam.examservice.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * many_question
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManyQuestion implements Serializable {
    private String id;

    private String subjectId;

    private String subjectName;


    private String questionName;

    private String questionA;

    private String questionB;

    private String questionC;

    private String questionD;

    private String answer;

    private String chooseAnswer;

    public static final double score=4;
}