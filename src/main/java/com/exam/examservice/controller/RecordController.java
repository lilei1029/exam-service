package com.exam.examservice.controller;


import com.exam.examservice.beans.Paper;
import com.exam.examservice.beans.Record;
import com.exam.examservice.mapper.RecordMapper;
import com.exam.examservice.service.RecoreService;
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
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecoreService recoreService;

    @Autowired
    private RecordMapper recordMapper;

    @RequestMapping("/selectRecordPage")
    public Map<String,Object> selectRecordPage(Integer page, Integer limit, String studentId){
        Map<String,Object> result=new HashMap<>();
        List<Record> recordList=recoreService.selectPage(page,limit,studentId);
        Integer count=recordMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",recordList);
        return result;
    }

    @RequestMapping("/selectRecord")
    public Map<String,Object>selectRecord(String studentId,String subjectName,String id){
        return recoreService.selectRecord(studentId,subjectName,id);
    }

    @RequestMapping("/selectRecordById")
    public Map<String,Object>selectRecordById(String studentId,String subjectId,String id){
        return recoreService.selectRecordById(studentId,subjectId,id);
    }
}
