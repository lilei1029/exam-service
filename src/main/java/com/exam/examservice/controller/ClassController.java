package com.exam.examservice.controller;

import com.exam.examservice.beans.Class;
import com.exam.examservice.mapper.ClassMapper;
import com.exam.examservice.service.ClassService;
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
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private ClassService classService;



    @RequestMapping("/selectClassPage")
    public Map<String,Object>selectClassPage(Integer page,Integer limit
            ,@RequestParam(value = "searchName",required = false) String searchName){
        Map<String,Object> result=new HashMap<>();
        List<Class> classList=classService.selectClassPage(page,limit,searchName);
        Integer count=classMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",classList);
        return result;
    }

    @RequestMapping("/addClass")
    public Map<String,Object> addClass(Class record){
        return classService.addClass(record);
    }

    @RequestMapping("/selectById")
    public Map<String,Object> selectById(String id){
        return classService.selectById(id);
    }

    @RequestMapping("/updateClass")
    public Map<String,Object>updateClass(Class cs){
        return classService.updateClass(cs);
    }

    @RequestMapping("/deleteClass")
    public Map<String,Object>deletsClass(String[] ids){
        return classService.deleteClass(ids);
    }
}
