package com.exam.examservice.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class
 * @author 
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Class implements Serializable {
    private String id;

    private String className;

}