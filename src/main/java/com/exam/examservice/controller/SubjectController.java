package com.exam.examservice.controller;

import com.exam.examservice.beans.Subject;
import com.exam.examservice.mapper.SubjectMapper;
import com.exam.examservice.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LILEI
 */
@RequestMapping("/subject")
@RestController
public class SubjectController {
    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/selectSubjectPage")
    public Map<String,Object> selectSubjectPage(Integer page, Integer limit
            , @RequestParam(value = "searchName",required = false) String searchName){
        Map<String,Object> result=new HashMap<>();
        List<Subject> List=subjectService.selectSubjectPage(page,limit,searchName);
        Integer count=subjectMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",List);
        return result;
    }

    @RequestMapping("/addSubject")
    public Map<String,Object> addSubject(Subject record){
        return subjectService.addSubject(record);
    }

    @RequestMapping("/selectById")
    public Map<String,Object> selectById(String id){
        return subjectService.selectById(id);
    }

    @RequestMapping("/updateSubject")
    public Map<String,Object>updateSubject(Subject s){
        return subjectService.updateClass(s);
    }

    @RequestMapping("/deleteSubject")
    public Map<String,Object>deleteSubject(String[] ids){
        return subjectService.deleteClass(ids);
    }
}
