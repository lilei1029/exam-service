package com.exam.examservice.controller;

import com.exam.examservice.beans.Exam;
import com.exam.examservice.mapper.ExamMapper;
import com.exam.examservice.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LILEI
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamMapper examMapper;


    @RequestMapping("/selectExamPage")
    public Map<String,Object> selectExamPage(Integer page, Integer limit){
        Map<String,Object> result=new HashMap<>();
        List<Exam> examList=examService.selectExamPage(page,limit);
        Integer count=examMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",examList);
        return result;
    }


    @RequestMapping("/selectSubjectAll")
    public Map<String,Object> selectSubjectAll(){
        return examService.selectSubjectAll();
    }

    @RequestMapping("/releaseExam")
    public Map<String,Object>releaseExam(Exam exam){
        return examService.releaseExam(exam);
    }

    @RequestMapping("/updateExam")
    public Map<String,Object> updateExam(String id){
        return examService.updateExam(id);
    }

    @RequestMapping("/deleteByIds")
    public Map<String,Object>deleteByIds(String[] ids){
        return examService.deleteByIds(ids);
    }

    @RequestMapping("/selectById")
    public Map<String,Object>selectById(String id){
        return examService.selectById(id);
    }

    @RequestMapping("/updateById")
    public Map<String,Object>updateById(String id, Date startTime, Date endTime){
        return examService.updateById(id,startTime,endTime);
    }
}
