package com.exam.examservice.service;

import com.exam.examservice.beans.*;
import com.exam.examservice.beans.Number;
import com.exam.examservice.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author LILEI
 */
@Service
public class PaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ManyQuestionMapper manyQuestionMapper;

    @Autowired
    private JudgeQuestionMapper judgeQuestionMapper;

    @Autowired
    private NumberMapper numberMapper;

    public List<Paper> selectPaperPage(Integer page, Integer limit, String searchName, String subjectId) {
//        //第一条从哪里开始开始
//        Integer firstIndex=(page-1)*limit;
//        //最后一条到哪里结束
//        Integer lastIndex=page*limit;
        PageHelper.startPage(page, limit);
        List<Paper> paperList = paperMapper.selectPage(searchName, subjectId);
        for (int i = 0; i < paperList.size(); i++) {
            String subjectID = paperList.get(i).getSubjectId();
            Subject subject = subjectMapper.selectById(subjectID);
            paperList.get(i).setSubjectName(subject.getSubjectName());
        }
        return paperList;
    }

    public Map<String, Object> selectSubjectAll() {
        Map<String, Object> result = new HashMap<>();
        List<Subject> subjectList = subjectMapper.selectAll();
        result.put("code", 200);
        result.put("data", subjectList);
        return result;
    }

    public Map<String, Object> testPaper(String paperName, Integer qNumber, Integer mNumber, Integer jNumber,
                                         String subjectId, Integer testTime, String createUser) {

        Map<String, Object> result = new HashMap<>();
        if (paperName == null || jNumber == null || qNumber == null || mNumber == null || testTime == null || createUser == null) {
            result.put("code", 400);
            result.put("msg", "信息不能为空");
            return result;

        } else {

            Integer Qconut = questionMapper.selectSubjectCount(subjectId);
            Integer Mcount = manyQuestionMapper.selectSubjectCount(subjectId);
            Integer Jcount = judgeQuestionMapper.selectSubjectCount(subjectId);

            Subject subject = subjectMapper.selectById(subjectId);
            if (Qconut < qNumber || Qconut.equals(qNumber)) {
                result.put("code", 400);
                result.put("msg", "输入单选题数量过多，" + subject.getSubjectName() + "单选题题库数量不足，请重新输入！");
                return result;

            }
            if (Mcount < mNumber || Mcount.equals(mNumber)) {
                result.put("code", 400);
                result.put("msg", "输入多选题数量过多，" + subject.getSubjectName() + "多选题题库数量不足，请重新输入！");
                return result;
            }
            if (Jcount < jNumber || Jcount.equals(jNumber)) {
                result.put("code", 400);
                result.put("msg", "输入判断题数量过多，" + subject.getSubjectName() + "判断题题库数量不足，请重新输入！");
                return result;
            }
            Random r = new Random();
            int totalQuestion = Qconut - qNumber;
            int lineQuestion = r.nextInt(totalQuestion);

            int totalQuestionDouble = Mcount - mNumber;
            int lineQuestionDouble = r.nextInt(totalQuestionDouble);

            int totalQuestionJudege = Jcount - jNumber;
            int lineQuestionJudege = r.nextInt(totalQuestionJudege);


            List<Question> question = questionMapper.selectLimitCount(lineQuestion, qNumber, subjectId);
            List<ManyQuestion> mQuestion = manyQuestionMapper.selectLimitCount(lineQuestionDouble, mNumber, subjectId);
            List<JudgeQuestion> jQuestion = judgeQuestionMapper.selectLimitCount(lineQuestionJudege, jNumber, subjectId);

            Content content = new Content();
            content.setQuestion(question);
            content.setManyQuestion(mQuestion);
            content.setJudgeQuestion(jQuestion);
            String str = com.alibaba.fastjson.JSONObject.toJSONString(content);


            double paperScore = Question.score * qNumber + ManyQuestion.score * mNumber +
                    JudgeQuestion.score * jNumber;

            Paper paper = new Paper();
            paper.setId(String.valueOf(System.currentTimeMillis()));
            paper.setPaperName(paperName);
            paper.setTestTime(testTime);
            paper.setSubjectId(subjectId);
            paper.setCreateTime(new Date());
            paper.setCreateUser(createUser);
            paper.setContent(str);
            paper.setScore(String.valueOf(paperScore));
            paperMapper.insert(paper);

            Number number = new Number();
            number.setId(String.valueOf(System.currentTimeMillis()));
            number.setPaperId(paper.getId());
            number.setQNumber(qNumber);
            number.setMNumber(mNumber);
            number.setJNumber(jNumber);
            numberMapper.insert(number);
            result.put("code", 200);
            result.put("msg", "组卷成功");
            return result;
        }
    }

    public Map<String, Object> selectById(String id) {
        Map<String, Object> result = new HashMap<>();
        Paper paper = paperMapper.selectById(id);
        if (paper == null) {
            result.put("code", 400);
            result.put("msg", "试卷不存在");
            return result;
        } else {
            Number number=numberMapper.selectByPaperId(id);
            Subject subject=subjectMapper.selectById(paper.getSubjectId());
            result.put("code", 200);
            result.put("data", paper);
            result.put("number",number);
            result.put("subject",subject);
            return result;
        }
    }

    public Map<String,Object>deleteByIds(String[] ids){
        Map<String,Object> result=new HashMap<>();
//        paperMapper.deleteByIds(ids);
        if (ids!=null&&ids.length>0) {
            result.put("code", 200);
            result.put("msg", "删除成功");
            paperMapper.deleteByIds(ids);
            return result;
        }else {
            result.put("code", 400);
            result.put("msg", "删除失败");
            return result;
        }
    }

    public Map<String,Object>updatePaper(String id,String paperName,Integer testTime,String createUser,
                                         Integer qNumber,Integer mNumber,Integer jNumber,String subjectId){
        Map<String,Object>result=new HashMap<>();
        if (paperName==null||testTime==null||createUser==null||qNumber==null||mNumber==null||jNumber==null
                ||subjectId==null){
            result.put("code",400);
            result.put("msg","信息不能为空");
            return result;
        }else {
            Integer Qconut = questionMapper.selectSubjectCount(subjectId);
            Integer Mcount = manyQuestionMapper.selectSubjectCount(subjectId);
            Integer Jcount = judgeQuestionMapper.selectSubjectCount(subjectId);

            Subject subject = subjectMapper.selectById(subjectId);
            if (Qconut < qNumber || Qconut.equals(qNumber)) {
                result.put("code", 400);
                result.put("msg", "输入单选题数量过多，" + subject.getSubjectName() + "单选题题库数量不足，请重新输入！");
                return result;

            }
            if (Mcount < mNumber || Mcount.equals(mNumber)) {
                result.put("code", 400);
                result.put("msg", "输入多选题数量过多，" + subject.getSubjectName() + "多选题题库数量不足，请重新输入！");
                return result;
            }
            if (Jcount < jNumber || Jcount.equals(jNumber)) {
                result.put("code", 400);
                result.put("msg", "输入判断题数量过多，" + subject.getSubjectName() + "判断题题库数量不足，请重新输入！");
                return result;
            }
            Random r = new Random();
            int totalQuestion = Qconut - qNumber;
            int lineQuestion = r.nextInt(totalQuestion);

            int totalQuestionDouble = Mcount - mNumber;
            int lineQuestionDouble = r.nextInt(totalQuestionDouble);

            int totalQuestionJudege = Jcount - jNumber;
            int lineQuestionJudege = r.nextInt(totalQuestionJudege);


            List<Question> question = questionMapper.selectLimitCount(lineQuestion, qNumber, subjectId);
            List<ManyQuestion> mQuestion = manyQuestionMapper.selectLimitCount(lineQuestionDouble, mNumber, subjectId);
            List<JudgeQuestion> jQuestion = judgeQuestionMapper.selectLimitCount(lineQuestionJudege, jNumber, subjectId);

            Content content = new Content();
            content.setQuestion(question);
            content.setManyQuestion(mQuestion);
            content.setJudgeQuestion(jQuestion);
            String str = com.alibaba.fastjson.JSONObject.toJSONString(content);


            double paperScore = Question.score * qNumber + ManyQuestion.score * mNumber +
                    JudgeQuestion.score * jNumber;
            Paper paper=new Paper();
            paper.setId(id);
            paper.setSubjectId(subjectId);
            paper.setTestTime(testTime);
            paper.setPaperName(paperName);
            paper.setCreateUser(createUser);
            paper.setCreateTime(new Date());
            paper.setScore(String.valueOf(paperScore));
            paperMapper.updateById(paper);

            Number number=new Number();
            number.setPaperId(id);
            number.setQNumber(qNumber);
            number.setMNumber(mNumber);
            number.setJNumber(jNumber);
            numberMapper.updateByPaperId(number);

            result.put("code",200);
            result.put("msg","修改成功");
            return result;
        }
    }

//    public Map<String,Object> selectById(String id){
//        Map<String,Object>result=new HashMap<>();
//        Paper paper=paperMapper.selectById(id);
//        if (paper==null){
//            result.put("code",400);
//            result.put("msg","数据不存在");
//            return result;
//        }else {
//            result.put("code",200);
//            result.put("msg","查询成功");
//            result.put("data",paper);
//            return result;
//        }
//    }
}
