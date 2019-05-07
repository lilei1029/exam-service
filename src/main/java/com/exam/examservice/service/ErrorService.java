package com.exam.examservice.service;

import com.exam.examservice.beans.*;
import com.exam.examservice.mapper.JerrorMapper;
import com.exam.examservice.mapper.MerrorMapper;
import com.exam.examservice.mapper.QerrorMapper;
import com.exam.examservice.mapper.SubjectMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LILEI
 */
@Service
public class ErrorService {

    @Autowired
    private QerrorMapper qerrorMapper;

    @Autowired
    private MerrorMapper merrorMapper;

    @Autowired
    private JerrorMapper jerrorMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    public List<Qerror> selectQerrorPage(Integer page, Integer limit, String searchName, String subjectId){
        PageHelper.startPage(page,limit);
        List<Qerror> qerrorList=qerrorMapper.selectPage(searchName,subjectId);
        for (int i = 0; i < qerrorList.size(); i++) {
            String subjectID = qerrorList.get(i).getSubjectId();
            Subject subject= subjectMapper.selectById(subjectID);
            qerrorList.get(i).setSubjectName(subject.getSubjectName());
        }
        return qerrorList;
    }


    public List<Merror> selectMerrorPage(Integer page, Integer limit, String searchName, String subjectId){
        PageHelper.startPage(page,limit);
        List<Merror> merrorList=merrorMapper.selectPage(searchName,subjectId);
        for (int i = 0; i < merrorList.size(); i++) {
            String subjectID = merrorList.get(i).getSubjectId();
            Subject subject= subjectMapper.selectById(subjectID);
            merrorList.get(i).setSubjectName(subject.getSubjectName());
        }
        return merrorList;
    }

    public List<Jerror> selectJerrorPage(Integer page, Integer limit, String searchName, String subjectId){
        PageHelper.startPage(page,limit);
        List<Jerror> jerrorList=jerrorMapper.selectPage(searchName,subjectId);
        for (int i = 0; i < jerrorList.size(); i++) {
            String subjectID = jerrorList.get(i).getSubjectId();
            Subject subject= subjectMapper.selectById(subjectID);
            jerrorList.get(i).setSubjectName(subject.getSubjectName());
        }
        return jerrorList;
    }
    public Map<String,Object> selectSubjectAll(){
        Map<String,Object> result=new HashMap<>();
        List<Subject> subjectList=subjectMapper.selectAll();
        result.put("code",200);
        result.put("data",subjectList);
        return result;
    }

}
