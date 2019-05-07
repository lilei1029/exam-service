package com.exam.examservice.controller;

import com.exam.examservice.beans.Paper;
import com.exam.examservice.beans.Question;
import com.exam.examservice.mapper.PaperMapper;
import com.exam.examservice.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LILEI
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperService paperService;

    @Value("${file.path}")
    private String filePath;

    @RequestMapping("/selectPaperPage")
    public Map<String,Object> selectPaperPage(Integer page, Integer limit
            , @RequestParam(value = "searchName",required = false) String searchName
            , @RequestParam(value = "subjectId",required = false) String subjectId){
        Map<String,Object> result=new HashMap<>();
        List<Paper> paperList=paperService.selectPaperPage(page,limit,searchName,subjectId);
        Integer count=paperMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",paperList);
        return result;
    }

    @RequestMapping("/selectSubjectAll")
    public Map<String,Object>selectSubjectAll(){
        return paperService.selectSubjectAll();
    }

    @RequestMapping("/testPaper")
    public Map<String, Object> testPaper(@RequestParam(value = "paperName") String paperName
            , @RequestParam(value = "qNumber") Integer qNumber
            , @RequestParam(value = "mNumber") Integer mNumber
            , @RequestParam(value = "jNumber") Integer jNumber
            , @RequestParam(value = "testTime") Integer testTime
            ,@RequestParam(value = "createUser") String createUser
            ,@RequestParam(value = "subjectId") String subjectId) {
        return paperService.testPaper(paperName, qNumber, mNumber, jNumber, subjectId,testTime,createUser);
    }

    @RequestMapping("/selectById")
    public Map<String,Object>selectById(String id){
        return paperService.selectById(id);
    }
    @RequestMapping("/deleteByIds")
    public Map<String,Object>deleteByIds(String[] ids){
        return paperService.deleteByIds(ids);
    }

    @RequestMapping("/updatePaper")
    public Map<String,Object>updatePaper(String id,String paperName,Integer testTime,String createUser,
                                         Integer qNumber,Integer mNumber,Integer jNumber,String subjectId){
        return paperService.updatePaper(id,paperName,testTime,createUser,qNumber,mNumber,jNumber,subjectId);
    }
}
