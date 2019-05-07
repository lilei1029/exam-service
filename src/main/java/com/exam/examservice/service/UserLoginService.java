//package com.exam.examservice.service;
//
//import com.exam.examservice.beans.Admin;
//import com.exam.examservice.beans.Student;
//import com.exam.examservice.beans.Teacher;
//import com.exam.examservice.mapper.AdminMapper;
//import com.exam.examservice.mapper.StudentMapper;
//import com.exam.examservice.mapper.TeacherMapper;
//import com.exam.examservice.utils.MD5Utils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class UserLoginService {
//
//      @Autowired
//      private AdminMapper adminMapper;
//
//      @Autowired
//      private StudentMapper studentMapper;
//
//      @Autowired
//      private TeacherMapper teacherMapper;
//
//
//      public Map<String,Object> userLogin(String userName, String password, String role ){
//          Map<String,Object> result=new HashMap<>();
//          Admin admin=adminMapper.findByUserName(userName, MD5Utils.encode(password),role);
//          Student student=studentMapper.findByUserName(userName,MD5Utils.encode(password),role);
//          Teacher teacher=teacherMapper.findByUserName(userName,MD5Utils.encode(password),role);
//          if (admin!=null) {
//              result.put("code", 200);
//              result.put("msg", "登录成功");
//              return result;
//          } else if (student!=null){
//              result.put("code",200);
//              result.put("msg","登录成功");
//              return result;
//          }else if (teacher!=null){
//              result.put("code",200);
//              result.put("msg","登录成功");
//              return result;
//          }else {
//              result.put("code",400);
//              result.put("msg","账号密码错误或角色不匹配");
//              return result;
//          }
//
//
//      }
//}
