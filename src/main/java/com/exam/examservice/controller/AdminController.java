package com.exam.examservice.controller;


import com.exam.examservice.beans.Admin;
import com.exam.examservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author LILEI
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/myself")
    public Map<String,Object> myself(String userName){
        return adminService.msg(userName);
    }

    @RequestMapping("/updateAdmin")
    public Map<String,Object> updateAdmin(Admin admin){
        return adminService.updateAdmin(admin);

    }

    @RequestMapping("/updatePassword")
    public Map<String,Object> updatePassword(String userName,String oldPassword,String password){
        return adminService.updatePassword(userName,oldPassword,password);

    }
}
