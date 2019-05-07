package com.exam.examservice.service;

import com.exam.examservice.beans.Number;
import com.exam.examservice.beans.Paper;
import com.exam.examservice.beans.Record;
import com.exam.examservice.beans.Subject;
import com.exam.examservice.mapper.NumberMapper;
import com.exam.examservice.mapper.PaperMapper;
import com.exam.examservice.mapper.RecordMapper;
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
public class RecoreService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private NumberMapper numberMapper;

    public List<Record> selectPage(Integer page, Integer limit, String studentId) {
        Map<String, Object> relult = new HashMap<>();

        PageHelper.startPage(page, limit);
        List<Record> recordList = recordMapper.selectByStudentId(studentId);

        for (int i = 0; i < recordList.size(); i++) {
            String subjectId = recordList.get(i).getSubjectId();
            Subject subject = subjectMapper.selectById(subjectId);
            recordList.get(i).setSubjectName(subject.getSubjectName());

            String paperId = recordList.get(i).getPaperId();
            Paper paper = paperMapper.selectById(paperId);
            recordList.get(i).setPaperName(paper.getPaperName());

        }
        return recordList;
    }

    public Map<String, Object> selectRecord(String studentId, String subjectName,String id) {
        Map<String, Object> result = new HashMap<>();
        if (studentId == null || subjectName == null) {
            result.put("code", 400);
            result.put("msg", "该考试记录不存在");
            return result;
        } else {
            Subject subject = subjectMapper.selectByName(subjectName);
            String subjectId = subject.getId();
            Record record = recordMapper.selectRecord(studentId, subjectId,id);
            Number number = numberMapper.selectByPaperId(record.getPaperId());
            Paper paper = paperMapper.selectById(record.getPaperId());
            result.put("code", 200);
            result.put("data", record);
            result.put("number", number);
            result.put("paper", paper);
            return result;

        }
    }

    public Map<String, Object> selectRecordById(String studentId, String subjectId,String id) {
        Map<String, Object> result = new HashMap<>();
        if (studentId == null || subjectId == null) {
            result.put("code", 400);
            result.put("msg", "该考试记录不存在");
            return result;
        } else {
            Record record = recordMapper.selectRecord(studentId, subjectId,id);
            result.put("code", 200);
            result.put("data", record);
            return result;

        }
    }
}
