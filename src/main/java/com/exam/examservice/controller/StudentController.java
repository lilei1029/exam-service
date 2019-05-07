package com.exam.examservice.controller;

import com.exam.examservice.beans.Exam;
import com.exam.examservice.beans.Record;
import com.exam.examservice.mapper.ExamMapper;
import com.exam.examservice.mapper.StudentMapper;
import com.exam.examservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LILEI
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping("/selectPage")
    public Map<String,Object> selectExamPage(Integer page, Integer limit){
        Map<String,Object> result=new HashMap<>();
        List<Exam> examList=studentService.selectExamPage(page,limit);
        Integer count=examMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",examList);
        return result;
    }

    @RequestMapping("/selectExamId")
    public Map<String,Object>selectExamId(String subjectName,String studentId) {
        return studentService.selectExamId(subjectName,studentId);
    }

    @RequestMapping("/selectPaperById")
    public Map<String,Object>selectPaperById(String subjectId) {
        return studentService.selectPaperById(subjectId);
    }

    @RequestMapping("/selectByStudentId")
    public Map<String,Object>selectByStudentId(String studentId){
        return studentService.selectByStudentId(studentId);
    }

    @RequestMapping("/addExamRecord")
    public Map<String,Object>addExamRecord(String content, String studentId,String paperId,String subjectId,String studentName) throws UnsupportedEncodingException {
        return studentService.addExamRecord(content,studentId,paperId,subjectId,studentName);
    }
    @RequestMapping("/updatePassword")
    public Map<String,Object>updatePassword(String studentId,String oldPassword,String password,String againPassword ) {
        return studentService.updatePassword(studentId,oldPassword,password,againPassword);
    }

    @RequestMapping("/selectAllPage")
    public Map<String,Object> selectAllPage(Integer page, Integer limit
            ,@RequestParam(value = "searchName",required = false) String searchName
            ,@RequestParam(value = "searchId",required = false) String searchId){
        Map<String,Object> result=new HashMap<>();
        List<Record> recordList=studentService.selectAllPage(page,limit,searchName,searchId);
        Integer count=studentMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",recordList);
        return result;
    }
}
