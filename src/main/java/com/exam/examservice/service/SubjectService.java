package com.exam.examservice.service;


import com.exam.examservice.beans.Subject;
import com.exam.examservice.mapper.SubjectMapper;
import com.exam.examservice.utils.IdGenerator;
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
public class SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    private IdGenerator idWorker = new IdGenerator(0, 0);;


    public List<Subject> selectSubjectPage(Integer page, Integer limit, String searchName){
//        //第一条从哪里开始开始
//        Integer firstIndex=(page-1)*limit;
//        //最后一条到哪里结束
//        Integer lastIndex=page*limit;
        PageHelper.startPage(page,limit);
        List<Subject> List=subjectMapper.selectPage(searchName);
        return List;
    }

    public Map<String,Object> addSubject(Subject s){
        Map<String,Object> result=new HashMap<>();
        if (s==null){
            result.put("code",400);
            result.put("msg","参数不能为空");
            return result;
        }else {
            Subject subject = subjectMapper.selectByName(s.getSubjectName());
            if (subject!=null){
                result.put("code",404);
                result.put("msg","班级已经存在!");
                return result;
            }else {
                Subject sub=new Subject();
//                c.setId(String.valueOf(idWorker.nextId()));
                sub.setId(String.valueOf(System.currentTimeMillis()));
                sub.setSubjectName(s.getSubjectName());
                try {
                    subjectMapper.insert(sub);
                } catch (Exception e ) {
                    e.printStackTrace();
                }
                result.put("code",200);
                result.put("msg","添加成功");
                return result;
            }
        }
    }

    public Map<String,Object> selectById(String id){
        Map<String,Object> result=new HashMap<>();
        Subject subject=subjectMapper.selectById(id);
        if (subject==null){
            result.put("code",400);
            result.put("msg","课程不存在");
            return result;
        }else {
            result.put("code",200);
            result.put("data",subject);
            return result;
        }

    }

    public Map<String,Object>updateClass(Subject s){
        Map<String,Object> result=new HashMap<>();
        if (s!=null){
            Subject subject=subjectMapper.selectById(s.getId());
            subject.setId(s.getId());
            subject.setSubjectName(s.getSubjectName());
            subjectMapper.updateById(subject);
            result.put("code",200);
            result.put("msg","修改成功");
        }else {
            result.put("code",400);
            result.put("msg","数据异常");
        }
        return result;
    }

    public Map<String,Object>deleteClass(String[] ids){
        Map<String,Object> result=new HashMap<>();
        if (ids!=null&&ids.length>0){
            result.put("code",200);
            result.put("msg","删除成功");
            subjectMapper.deleteByIds(ids);
        }
        return result;
    }
}
