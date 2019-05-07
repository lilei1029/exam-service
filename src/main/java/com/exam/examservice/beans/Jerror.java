package com.exam.examservice.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jerror implements Serializable {

    private String id;

    private String questionId;

    private String subjectId;

    private String subjectName;

    private String questionName;

    private int errorCount;

    private int answerCount;
}
