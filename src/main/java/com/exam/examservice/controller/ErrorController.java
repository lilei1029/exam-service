package com.exam.examservice.controller;


import com.exam.examservice.beans.Jerror;
import com.exam.examservice.beans.Merror;
import com.exam.examservice.beans.Qerror;
import com.exam.examservice.beans.Question;
import com.exam.examservice.mapper.JerrorMapper;
import com.exam.examservice.mapper.MerrorMapper;
import com.exam.examservice.mapper.QerrorMapper;
import com.exam.examservice.service.ErrorService;
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
@RequestMapping("/error")
public class ErrorController {

    @Autowired
    private ErrorService errorService;

    @Autowired
    private QerrorMapper qerrorMapper;

    @Autowired
    private MerrorMapper merrorMapper;

    @Autowired
    private JerrorMapper jerrorMapper;

    @RequestMapping("/selectQerrorPage")
    public Map<String,Object> selectQerrorPage(Integer page, Integer limit
            , @RequestParam(value = "searchName",required = false) String searchName
            , @RequestParam(value = "subjectId",required = false) String subjectId){
        Map<String,Object> result=new HashMap<>();
        List<Qerror> qerrorList=errorService.selectQerrorPage(page,limit,searchName,subjectId);
        Integer count=qerrorMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",qerrorList);
        return result;
    }


    @RequestMapping("/selectMerrorPage")
    public Map<String,Object> selectMerrorPage(Integer page, Integer limit
            , @RequestParam(value = "searchName",required = false) String searchName
            , @RequestParam(value = "subjectId",required = false) String subjectId){
        Map<String,Object> result=new HashMap<>();
        List<Merror> merrorList=errorService.selectMerrorPage(page,limit,searchName,subjectId);
        Integer count=merrorMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",merrorList);
        return result;
    }

    @RequestMapping("/selectJerrorPage")
    public Map<String,Object> selectJerrorPage(Integer page, Integer limit
            , @RequestParam(value = "searchName",required = false) String searchName
            , @RequestParam(value = "subjectId",required = false) String subjectId){
        Map<String,Object> result=new HashMap<>();
        List<Jerror> jerrorList=errorService.selectJerrorPage(page,limit,searchName,subjectId);
        Integer count=jerrorMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",jerrorList);
        return result;
    }

    @RequestMapping("/selectSubjectAll")
    public Map<String,Object>selectSubjectAll(){
        return errorService.selectSubjectAll();
    }
}
