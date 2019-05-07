package com.exam.examservice.service;

import com.exam.examservice.beans.Class;
import com.exam.examservice.beans.Student;
import com.exam.examservice.beans.Teacher;
import com.exam.examservice.mapper.*;
import com.exam.examservice.utils.ImportExecl;
import com.exam.examservice.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.sun.javafx.collections.MappingChange;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private RecordMapper recordMapper;

    public  List<Student> selectStudentPage(Integer page , Integer limit, String searchName ,String classId) {
        Map<String, Object> result = new HashMap<>();
//        Integer firstIndex = (page - 1) * limit;
//        Integer lastIndex = page * limit;
        PageHelper.startPage(page,limit);
        List<Student> student = studentMapper.selectPage(searchName, classId);
        for (int i = 0; i < student.size(); i++) {
            String classID = student.get(i).getClassId();
            Class c = classMapper.selectById(classID);
            student.get(i).setClassName(c.getClassName());
        }
        return student;
    }

    public Map<String,Object> selectClassAll(){
        Map<String,Object> result=new HashMap<>();
        List<Class> classList=classMapper.selectAll();
        result.put("code",200);
        result.put("data",classList);
        return result;
    }

    public Map<String,Object> addStudent(Student student){
        Map<String,Object> result=new HashMap<>();
        Student s=studentMapper.findByStudentId(student.getStudentId());
        if (student ==null){
            result.put("code","400");
            result.put("msg", "信息不能为空");
            return result;
        }
        try {
            if (s!=null) {
                result.put("code", "401");
                result.put("msg", "学生已存在");
            }
            if (student.getClassId()==null||student.getClassId()=="")
            {
                result.put("code",403);
                result.put("msg","请选择班级");
                return result;
            }
            if (s==null) {
                Student stu=new Student();
                stu.setId(String.valueOf(System.currentTimeMillis()));
                stu.setStudentId(student.getStudentId());
                stu.setNickName(student.getNickName());
                stu.setClassId(student.getClassId());
                stu.setAge(student.getAge());
                stu.setGender(student.getGender());
                stu.setPassword(MD5Utils.encode("123456"));
                stu.setRole(student.getRole());
                result.put("code", "200");
                result.put("msg", "添加成功");
                studentMapper.insert(stu);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("msg","404");
            result.put("msg", "系统异常");
        }
        return result;
    }
    public Map<String,Object>selectStudentById(String id){
        Map<String,Object>result=new HashMap<>();
        Student student=studentMapper.findById(id);
        if (student==null){
            result.put("code",400);
            result.put("msg","该学生不存在");
            return result;
        }else {
            result.put("code",200);
            result.put("data",student);
            return result;
        }
    }
    public Map<String,Object>updataStudent(Student student){
        Map<String,Object>result=new HashMap<>();
        student.setPassword(MD5Utils.encode(student.getPassword()));
        studentMapper.updateById(student);
        result.put("code",200);
        result.put("msg","修改成功");
        return result;
    }

    public Map<String,Object>deleteByIds(String[] ids){
        Map<String,Object> result=new HashMap<>();
        List<String> list=new ArrayList();
        if (ids!=null&&ids.length>0) {
            for (String id:ids)
            {
                Student student=studentMapper.findById(id);
                String studentId=student.getStudentId();
                list.add(studentId);
            }
            String[] studentIds=list.toArray(new String[list.size()]);
            recordMapper.deleteByIds(studentIds);
            studentMapper.deleteByIds(ids);
            result.put("code", 200);
            result.put("msg", "删除成功");


            return result;
        }else {
            result.put("code", 400);
            result.put("msg", "删除失败");
            return result;
        }
    }

    public  List<Teacher> selectTeacherPage(Integer page , Integer limit, String searchName) {
        Map<String, Object> result = new HashMap<>();
//        Integer firstIndex = (page - 1) * limit;
//        Integer lastIndex = page * limit;
        PageHelper.startPage(page,limit);
        List<Teacher> teacher = teacherMapper.selectPage(searchName);
        return teacher;
    }

    public Map<String,Object>addTeacher(Teacher teacher){
    Map<String,Object> result=new HashMap<>();
    Teacher th=teacherMapper.findByTeacherId(teacher.getTeacherId());
    if (th!=null){
        result.put("code",400);
        result.put("msg","该教师已经存在");
        return result;
    }else {
        Teacher t =new Teacher();
        t.setId(String.valueOf(System.currentTimeMillis()));
        t.setTeacherId(teacher.getTeacherId());
        t.setNickName(teacher.getNickName());
        t.setAge(teacher.getAge());
        t.setGender(teacher.getGender());
        t.setPassword(MD5Utils.encode("123456"));
        t.setRole(teacher.getRole());
        teacherMapper.insert(t);
        result.put("code",200);
        result.put("msg","添加成功");
        return result;
    }
    }

    public Map<String,Object>selectTeacherById(String id){
        Map<String,Object> result=new HashMap<>();
        Teacher teacher=teacherMapper.selectById(id);
        if (teacher==null){
            result.put("code",400);
            result.put("msg","该教师不存在");
            return result;
        }else {
            result.put("code",200);
            result.put("data",teacher);
            return result;
        }
    }
    public Map<String,Object>selectTeacherId(String teacherId){
        Map<String,Object> result=new HashMap<>();
        Teacher teacher=teacherMapper.findByTeacherId(teacherId);
        if (teacher==null){
            result.put("code",400);
            result.put("msg","该教师不存在");
            return result;
        }else {
            result.put("code",200);
            result.put("data",teacher);
            return result;
        }
    }

    public Map<String,Object>updateTeacher(Teacher teacher){
        Map<String,Object>result=new HashMap<>();
        teacher.setPassword(MD5Utils.encode(teacher.getPassword()));
        teacherMapper.updateById(teacher);
        result.put("code",200);
        result.put("msg","修改成功");
        return result;
    }

    public Map<String,Object>deleteTeacher(String[] ids){
        Map<String,Object> result=new HashMap<>();
        studentMapper.deleteByIds(ids);
        if (ids!=null&&ids.length>0) {
            result.put("code", 200);
            result.put("msg", "删除成功");
            teacherMapper.deleteByIds(ids);
            return result;
        }else {
            result.put("code", 400);
            result.put("msg", "删除失败");
            return result;
        }
    }
    public Map<String,Object>addStudentUpload(String filePath,HttpServletRequest request) throws  Exception {
        System.out.println("********************"+filePath);
        Map<String, Object> relust = new HashMap<>();
        ImportExecl poi = new ImportExecl();
        List<List<String>> list = poi.read(filePath);
        System.out.println("********************"+filePath);
        String classId=(String) request.getParameter("classId");
        System.out.println("---------------"+classId);
        Class c=classMapper.selectById(classId);
        List<Student> studentList = new Vector<>();
        relust.put("data", studentList);

        if (classId==null||classId==""||classId.equals("undefined")){
            relust.put("code",400);
            relust.put("msg","请选择班级!");
            return relust;
        }

        if (list.size() < 1) {
            relust.put("code", 500);
            relust.put("msg", "所传模板不正确！");
            return relust;
        }
        List<String> cell = list.get(0);
        if (!"学号".equals(cell.get(0)) && !"姓名".equals(cell.get(1)) && !"年龄".equals(cell.get(2)) &&
                !"性别".equals(cell.get(3)) && !"密码".equals(cell.get(4))) {
            relust.put("code", 500);
            relust.put("msg", "所传模板不正确！");
        }
        if (list != null) {
            for (int i=1;i<list.size();i++) {
                List<String>cellList=list.get(i);
                Student student = new Student();
                Student sd=studentMapper.findByStudentId(cellList.get(0));
                if (sd!=null){
                    relust.put("code",202);
                    relust.put("msg","添加成功，有学生已存在！");
                    studentList.add(sd);
                    continue;  //继续下个循环
                }
                student.setId(String.valueOf(System.currentTimeMillis()));
                student.setStudentId(cellList.get(0));
                student.setNickName(cellList.get(1));
                student.setClassId(classId);
                student.setGender(cellList.get(3));
                student.setAge(cellList.get(2));
                student.setPassword(MD5Utils.encode("123456"));
                student.setRole("1");
                studentMapper.insert(student);
                relust.put("code",200);
                relust.put("msg","数据导入成功");

            }
        }
        return relust;
    }

    public Map<String,Object>addTeacherUpload(String filePath) throws  Exception {
        System.out.println("********************"+filePath);
        Map<String, Object> relsult = new HashMap<>();
        ImportExecl poi = new ImportExecl();
        List<List<String>> list = poi.read(filePath);
        List<Teacher> teacherList=new Vector<>();
        relsult.put("data",teacherList);
        System.out.println("********************"+filePath);

        if (list.size() < 1) {
            relsult.put("code", 500);
            relsult.put("msg", "所传模板不正确！");
            return relsult;
        }
        List<String> cell = list.get(0);
        if (!"教师工号".equals(cell.get(0)) && !"姓名".equals(cell.get(1)) && !"性别".equals(cell.get(3)) &&
                !"年龄".equals(cell.get(3)) && !"密码".equals(cell.get(4))) {
            relsult.put("code", 500);
            relsult.put("msg", "所传模板不正确！");
        }
        if (list != null) {
            for (int i=1;i<list.size();i++) {
                List<String>cellList=list.get(i);
                Teacher teacher = new Teacher();
                Teacher th=teacherMapper.findByTeacherId(cellList.get(0));
                if (th!=null){
                    relsult.put("code",202);
                    relsult.put("msg","添加成功，有教师已存在");
                    teacherList.add(th);
                    continue;  //已存在继续下个循环
                }
                teacher.setId(String.valueOf(System.currentTimeMillis()));
                teacher.setTeacherId(cellList.get(0));
                teacher.setNickName(cellList.get(1));
                teacher.setGender(cellList.get(2));
                teacher.setAge(cellList.get(3));
                teacher.setPassword(MD5Utils.encode("123456"));
                teacher.setRole("2");
                teacherMapper.insert(teacher);
                relsult.put("code",200);
                relsult.put("msg","数据导入成功");

            }
        }
        return relsult;
    }
}
