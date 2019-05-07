package com.exam.examservice.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LILEI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Number implements Serializable {

    private String id;

    private String paperId;

    private int qNumber;

    private int mNumber;

    private int jNumber;
}
