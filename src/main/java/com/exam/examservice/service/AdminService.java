package com.exam.examservice.service;

import com.exam.examservice.beans.Admin;
import com.exam.examservice.mapper.AdminMapper;
import com.exam.examservice.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Map<String,Object> msg(String userName){
        Map<String,Object>result=new HashMap<>();
        if (userName==null||userName=="")
        {
            result.put("code",400);
            result.put("msg","该用户不存在，无法查看个人信息！");
            return result;
        }
        Admin admin=adminMapper.selectByUserName(userName);
        result.put("code",200);
        result.put("msg","获取个人信息成功！");
        result.put("data",admin);
        return result;

    }

    public Map<String,Object>updateAdmin(Admin admin){
        Map<String,Object>result=new HashMap<>();
        if (admin==null){
            result.put("code",400);
            result.put("msg","需要修改的信息不能为空！");
            return result;
        }
        Admin ad=adminMapper.selectByUserName(admin.getUserName());
        Admin admin1=new Admin();
        admin1.setId(ad.getId());
        admin1.setUserName(admin.getUserName());
        admin1.setNickName(admin.getNickName());
        admin1.setGender(admin.getGender());
        admin1.setEmail(admin.getEmail());
        admin1.setTelephone(admin.getTelephone());
        adminMapper.updateById(admin1);
        result.put("code",200);
        result.put("msg","修改信息成功");
        return result;

    }

    public Map<String,Object>updatePassword(String userName,String oldPassword,String password){
        Map<String,Object>result=new HashMap<>();
        Admin admin =adminMapper.selectByUserName(userName);
        if (!MD5Utils.encode(oldPassword).equals(admin.getPassword())){
            result.put("code",400);
            result.put("msg","原密码不正确！");
            return result;
        }
        Admin admin1=new Admin();
        admin1.setId(admin.getId());
        admin1.setNickName(admin.getNickName());
        admin1.setUserName(admin.getUserName());
        admin1.setPassword(MD5Utils.encode(password));
        admin1.setTelephone(admin.getTelephone());
        admin1.setEmail(admin.getEmail());
        admin1.setGender(admin.getGender());
        adminMapper.updateById(admin1);
        result.put("code",200);
        result.put("msg","修改密码成功！");
        return result;
    }

    }
