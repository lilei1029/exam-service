package com.exam.examservice.controller;

import com.exam.examservice.beans.Student;
import com.exam.examservice.beans.Subject;
import com.exam.examservice.beans.Teacher;
import com.exam.examservice.mapper.StudentMapper;
import com.exam.examservice.mapper.TeacherMapper;
import com.exam.examservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Value("${file.path}")
    private String filePath;

    @RequestMapping("/selectStudentPage")
    public Map<String,Object>selectStudentPage(Integer page, Integer limit
            , @RequestParam(value = "searchName",required = false) String searchName
            ,@RequestParam(value = "classId",required = false) String classId){
        Map<String,Object> reslult=new HashMap<>();
            int count =studentMapper.selectCount();
        List<Student>list=userService.selectStudentPage(page,limit,searchName,classId);
            reslult.put("code",200);
            reslult.put("count",count);
            reslult.put("data",list);
            return  reslult;
    }

    @RequestMapping("/selectClassAll")
    public Map<String,Object> selectClassAll(){
        return userService.selectClassAll();
    }

    @RequestMapping("/addStudent")
    public Map<String,Object>addStudent(Student student){
        return userService.addStudent(student);
    }

    @RequestMapping("/selectStudentById")
    public Map<String,Object>selectStudentById(String id){
        return userService.selectStudentById(id);
    }

    @RequestMapping("/updateStudent")
    public Map<String,Object>updateStudent(Student student){
        return userService.updataStudent(student);
    }

    @RequestMapping("/deleteByIds")
    public Map<String,Object>deleteByIds(String[] ids){
        return userService.deleteByIds(ids);
    }
    @RequestMapping("/selectTeacherPage")
    public Map<String,Object>selectTeacherPage(Integer page, Integer limit
            , @RequestParam(value = "searchName",required = false) String searchName){
        Map<String,Object> reslult=new HashMap<>();
        int count =teacherMapper.selectCount();
        List<Teacher>list=userService.selectTeacherPage(page,limit,searchName);
        reslult.put("code",200);
        reslult.put("count",count);
        reslult.put("data",list);
        return  reslult;
    }

    @RequestMapping("/addTeacher")
    public Map<String,Object>addTeacher(Teacher teacher){
        return userService.addTeacher(teacher);
    }

    @RequestMapping("/selectTeacherById")
    public Map<String,Object>selectTeacherById(String id){
        return userService.selectTeacherById(id);
    }
    @RequestMapping("/selectTeacherId")
    public Map<String,Object>selectTeacherId(String teacherId){
        return userService.selectTeacherId(teacherId);
    }

    @RequestMapping("/updateTeacher")
    public Map<String,Object>updateTeacher(Teacher teacher){
        return userService.updateTeacher(teacher);
    }

    @RequestMapping("/deleteTeacherById")
    public Map<String,Object>deleteTeacherById(String[] ids){
        return userService.deleteTeacher(ids);
    }

    @RequestMapping("/addStudentUpload")
    public Map<String,Object>addStudentUpload(@RequestParam(value = "file") MultipartFile multipartFile, HttpServletRequest request) throws  Exception{
        Map<String,Object>result=new HashMap<>();
        //lf
        String className = (String)request.getParameter("classId");
        System.out.println("++++++++============"+className);
        String uploadFilePath=multipartFile.getOriginalFilename();
        //截取上传文件后缀
        String uploadFileSuffix=uploadFilePath.substring(uploadFilePath.indexOf('.')+1,uploadFilePath.length());
        String fileName= UUID.randomUUID()+"."+uploadFileSuffix;

        //将文件放入磁盘读取
        try {
            File dest = new File(filePath +File.separator+ fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            multipartFile.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userService.addStudentUpload(filePath+File.separator+fileName, request);
    }

    @RequestMapping("/addTeacherUpload")
    public Map<String,Object>addTeacherUpload(@RequestParam(value = "file") MultipartFile multipartFile) throws  Exception{
     Map<String,Object>result=new HashMap<>();
     String uploadFilePath=multipartFile.getOriginalFilename();
     //截取上传文件后缀
        String uploadFileSuffix=uploadFilePath.substring(uploadFilePath.indexOf('.')+1,uploadFilePath.length());
        String fileName= UUID.randomUUID()+"."+uploadFileSuffix;

        //将文件放入磁盘读取
        try {
            File dest = new File(filePath +File.separator+ fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            multipartFile.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userService.addTeacherUpload(filePath+File.separator+fileName);
    }
}
