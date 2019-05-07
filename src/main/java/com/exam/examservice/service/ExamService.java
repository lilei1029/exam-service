package com.exam.examservice.service;


import com.exam.examservice.beans.Exam;
import com.exam.examservice.beans.Number;
import com.exam.examservice.beans.Paper;
import com.exam.examservice.beans.Subject;
import com.exam.examservice.mapper.ExamMapper;
import com.exam.examservice.mapper.SubjectMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExamService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    public List<Exam> selectExamPage(Integer page, Integer limit) {
//        //第一条从哪里开始开始
//        Integer firstIndex=(page-1)*limit;
//        //最后一条到哪里结束
//        Integer lastIndex=page*limit;
        PageHelper.startPage(page, limit);
        List<Exam> examList = examMapper.selectPage();
        for (int i = 0; i < examList.size(); i++) {
            String subjectID = examList.get(i).getSubjectId();
            Subject subject = subjectMapper.selectById(subjectID);
            examList.get(i).setSubjectName(subject.getSubjectName());

            String st = examList.get(i).getState();
            if (st.equals("0")) {
                examList.get(i).setStateValue("已结束");
            }
            if (st.equals("1")) {
                examList.get(i).setStateValue("正在进行");
            }
        }
        return examList;
    }

    public Map<String, Object> selectSubjectAll() {
        Map<String, Object> result = new HashMap<>();
        List<Subject> subjectList = subjectMapper.selectAll();
        result.put("code", 200);
        result.put("data", subjectList);
        return result;
    }

    public Map<String, Object> releaseExam(Exam exam) {
        Map<String, Object> result = new HashMap<>();
        Exam ex=examMapper.selectBySubjectId(exam.getSubjectId());
        if (exam == null) {
            result.put("code", 400);
            result.put("msg", "信息不能为空");
            return result;
        }
        if (ex!=null&& "1".equals(ex.getState())){
            result.put("code",400);
            result.put("msg","该课程考试已发布");
            return result;
        }
        if (ex!=null&& "0".equals(ex.getState())){
            result.put("code",402);
            result.put("msg","该课程考试已发布,但是考试已经结束是否重新发布！");
            result.put("data",ex);
            return result;
        }
        else {
            Exam e = new Exam();
            e.setId(String.valueOf(System.currentTimeMillis()));
            e.setStartTime(exam.getStartTime());
            e.setEndTime(exam.getEndTime());
            e.setSubjectId(exam.getSubjectId());
            e.setState("1");
            examMapper.insert(e);
            result.put("code", 200);
            result.put("msg", "考试发布成功");
            return result;
        }
    }

    public Map<String, Object> updateExam(String id) {
        Map<String, Object> result = new HashMap<>();
        Exam exam = examMapper.selectById(id);
        if (exam==null){
            result.put("code",400);
            result.put("msg","该课程考试不存在");
            return result;
        }else {
            Exam e=new Exam();
            e.setId(id);
            e.setStartTime(exam.getStartTime());
            e.setEndTime(exam.getEndTime());
            e.setSubjectId(exam.getSubjectId());
            if ("1".equals(exam.getState())) {
                e.setState("0");
                result.put("code",200);
                result.put("msg","已结束考试");
            }
            if ("0".equals(exam.getState())) {
                e.setState("1");
                result.put("code",202);
                result.put("msg","已重新发布考试");
            }
            examMapper.updateById(e);
            return result;
        }

    }

    public Map<String,Object>deleteByIds(String[] ids){
        Map<String,Object> result=new HashMap<>();
        if (ids!=null&&ids.length>0) {
            result.put("code", 200);
            result.put("msg", "删除成功");
            examMapper.deleteByIds(ids);
            return result;
        }else {
            result.put("code", 400);
            result.put("msg", "删除失败");
            return result;
        }
    }

    public Map<String, Object> selectById(String id) {
        Map<String, Object> result = new HashMap<>();
        Exam exam = examMapper.selectById(id);
        if (exam == null) {
            result.put("code", 400);
            result.put("msg", "该课程考试不存在");
            return result;
        } else {
            result.put("code", 200);
            result.put("data", exam);
            return result;
        }
    }

    public Map<String,Object>updateById(String id, Date startTime, Date endTime){
        Map<String, Object> result = new HashMap<>();
        Exam e=examMapper.selectById(id);
        if (id==null||startTime==null||endTime==null){
            result.put("code",400);
            result.put("msg","信息不能为空");
            return result;
        }
        if (e==null){
            result.put("code",400);
            result.put("msg","该课程的考试不存在");
            return result;
        }else {
            Exam ex=new Exam();
            ex.setId(id);
            ex.setSubjectId(e.getSubjectId());
            ex.setStartTime(startTime);
            ex.setEndTime(endTime);
            ex.setState(e.getState());
            examMapper.updateById(ex);
            result.put("code",200);
            result.put("msg","修改成功");
            return result;
        }

    }
}
