package com.exam.examservice.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * admin
 * @author 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin implements Serializable {
    private String id;

    private String userName;

    private String nickName;

    private String gender;

    private String telephone;

    private String email;

    private String password;

    private String role;


}