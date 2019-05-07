package com.exam.examservice.service;

import com.alibaba.fastjson.JSONObject;
import com.exam.examservice.beans.*;
import com.exam.examservice.beans.Class;
import com.exam.examservice.beans.Number;
import com.exam.examservice.mapper.*;
import com.exam.examservice.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LILEI
 */
@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private NumberMapper numberMapper;

    @Autowired
    private QerrorMapper qerrorMapper;

    @Autowired
    private MerrorMapper merrorMapper;

    @Autowired
    private JerrorMapper jerrorMapper;

    public List<Exam> selectExamPage(Integer page, Integer limit) {
//        //第一条从哪里开始开始
//        Integer firstIndex=(page-1)*limit;
//        //最后一条到哪里结束
//        Integer lastIndex=page*limit;
        PageHelper.startPage(page, limit);
        List<Exam> examList = examMapper.selectPageByState();
        for (int i = 0; i < examList.size(); i++) {
            String subjectID = examList.get(i).getSubjectId();
            Subject subject = subjectMapper.selectById(subjectID);
            examList.get(i).setSubjectName(subject.getSubjectName());
//            String st = examList.get(i).getState();
//            if (st.equals("0")) {
//                examList.get(i).setStateValue("已结束");
//            }
//            if (st.equals("1")) {
//                examList.get(i).setStateValue("正在进行");
//            }
        }
        return examList;
    }

    public Map<String, Object> selectExamId(String subjectName, String studentId) {
        Map<String, Object> result = new HashMap<>();
        if (subjectName == null && studentId == null) {
            result.put("code", 400);
            result.put("msg", "该课程考试已结束！");
            return result;
        }
        Subject s = subjectMapper.selectByName(subjectName);
        String subjectId = s.getId();
//        Record record = recordMapper.selectBySubjectId(subjectId, studentId);
//        if (record != null) {
////            result.put("code", 400);
////            result.put("msg", "该课程你已经完成考试，请选择其他课程开始考试！");
//            return null;
//        } else {
            result.put("code", 200);
            result.put("data", s);
            return result;
//        }
//            int countPaper=paperMapper.selectSubjectCount(subjectId);
//            Random r=new Random();
//            int cPaper=countPaper-1;
//            int conut=r.nextInt(cPaper);
//            Paper paper=paperMapper.selectLimit(subjectId,conut);

    }

    public Map<String, Object> selectPaperById(String subjectId) {
        Map<String, Object> result = new HashMap<>();
        int countPaper = paperMapper.selectSubjectCount(subjectId);
        Random r = new Random();
        int cPaper = countPaper - 1;
        int conut = r.nextInt(cPaper);
        Paper paper = paperMapper.selectLimit(subjectId, conut);
        Number number = numberMapper.selectByPaperId(paper.getId());
        Subject subject = subjectMapper.selectById(subjectId);
        result.put("code", 200);
        result.put("data", paper);
        result.put("number", number);
        result.put("subject", subject);
        return result;
    }

    public Map<String, Object> selectByStudentId(String studentId) {
        Map<String, Object> result = new HashMap<>();
        if (studentId == null) {
            result.put("code", 200);
            result.put("msg", "该学生不存在");
            return result;
        } else {
            Student student = studentMapper.findByStudentId(studentId);
            Class c = classMapper.selectById(student.getClassId());
            student.setClassName(c.getClassName());
            result.put("code", 200);
            result.put("data", student);
            return result;
        }

    }

    public Map<String, Object> addExamRecord(String content, String studentId, String paperId, String subjectId, String studentName) throws UnsupportedEncodingException {
        Map<String, Object> result = new HashMap<>();
//        Student student = studentMapper.findByStudentId(studentId);
//        if (student==null) {
        Record record = new Record();
        //System.currentTimeMillis()  时间戳以毫秒为单位
        record.setId(String.valueOf(System.currentTimeMillis()));
        record.setPaperId(paperId);
        record.setStudentId(studentId);
        record.setSubjectId(subjectId);
        record.setStudentName(studentName);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        record.setAnswerTime(sdf.format(date));


        //将前端转码的数据转换回来
        String c = URLDecoder.decode(content, "utf-8");
        Content content1 = JSONObject.parseObject(c, Content.class);

        List<Question> questionContent = content1.getQuestion();
        double questionScore = 0;
        for (int i = 0; i < questionContent.size(); i++) {
            Qerror qerror = qerrorMapper.selectOne(questionContent.get(i).getId());
            if (questionContent.get(i).getChooseAnswer() == null || questionContent.get(i).getChooseAnswer() == "") {
                questionScore = 0;
                if (qerror == null) {
                    Qerror questionError1 = new Qerror();
                    questionError1.setId(String.valueOf(System.currentTimeMillis()));
                    questionError1.setQuestionId(questionContent.get(i).getId());
                    questionError1.setSubjectId(questionContent.get(i).getSubjectId());
                    questionError1.setQuestionName(questionContent.get(i).getQuestionName());
                    questionError1.setErrorCount(1);
                    questionError1.setAnswerCount(1);
                    qerrorMapper.insert(questionError1);
                } else {
                    qerror.setErrorCount(qerror.getErrorCount() + 1);
                    qerror.setAnswerCount(qerror.getAnswerCount() + 1);
                    qerrorMapper.updateById(qerror);
                }
            } else if (questionContent.get(i).getChooseAnswer().equals(questionContent.get(i).getAnswer())) {
                questionScore += Question.score;
                if (qerror==null) {
                    Qerror questionError1=new Qerror();
                    questionError1.setAnswerCount(1);
                    qerrorMapper.updateById(questionError1);
                }else {
                    qerror.setAnswerCount(qerror.getAnswerCount()+1);
                    qerrorMapper.updateById(qerror);
                }
            } else {
                if (qerror == null) {
                    Qerror questionError1=new Qerror();
                    questionError1.setId(String.valueOf(System.currentTimeMillis()));
                    questionError1.setQuestionId(questionContent.get(i).getId());
                    questionError1.setSubjectId(questionContent.get(i).getSubjectId());
                    questionError1.setQuestionName(questionContent.get(i).getQuestionName());
                    questionError1.setErrorCount(1);
                    questionError1.setAnswerCount(1);
                    qerrorMapper.insert(questionError1);
                } else {
                    qerror.setErrorCount(qerror.getErrorCount() + 1);
                    qerror.setAnswerCount(qerror.getAnswerCount() + 1);
                    qerrorMapper.updateById(qerror);
                }
            }

        }
        List<ManyQuestion> questionDoubleContent = content1.getManyQuestion();
        double questionsScore = 0;
        for (int j = 0; j < questionDoubleContent.size(); j++) {
            Merror merror = merrorMapper.selectOne( questionDoubleContent.get(j).getId());
            if (questionDoubleContent.get(j).getChooseAnswer() == null || questionDoubleContent.get(j).getChooseAnswer() == "") {
                questionsScore = 0;
                if (merror == null) {
                    Merror questionDoubleError1 = new Merror();
                    questionDoubleError1.setId(String.valueOf(System.currentTimeMillis()));
                    questionDoubleError1.setQuestionId(questionDoubleContent.get(j).getId());
                    questionDoubleError1.setSubjectId(questionDoubleContent.get(j).getSubjectId());
                    questionDoubleError1.setQuestionName(questionDoubleContent.get(j).getQuestionName());
                    questionDoubleError1.setErrorCount(1);
                    questionDoubleError1.setAnswerCount(1);
                    merrorMapper.insert(questionDoubleError1);
                } else {
                    merror.setErrorCount(merror.getErrorCount() + 1);
                    merror.setAnswerCount(merror.getAnswerCount() + 1);
                    merrorMapper.updateById(merror);
                }

            }
            else if (questionDoubleContent.get(j).getChooseAnswer().equals(questionDoubleContent.get(j).getAnswer())) {
                questionsScore += ManyQuestion.score;
                if (merror==null) {
                    Merror questionDoubleError1=new Merror();
                    questionDoubleError1.setAnswerCount(1);
                    merrorMapper.updateById(questionDoubleError1);
                }else {
                    merror.setAnswerCount(merror.getAnswerCount()+1);
                    merrorMapper.updateById(merror);
                }
            }
             else {
                if (merror == null) {
                    Merror questionDoubleError1=new Merror();
                    questionDoubleError1.setId(String.valueOf(System.currentTimeMillis()));
                    questionDoubleError1.setQuestionId(questionDoubleContent.get(j).getId());
                    questionDoubleError1.setSubjectId(questionDoubleContent.get(j).getSubjectId());
                    questionDoubleError1.setQuestionName(questionDoubleContent.get(j).getQuestionName());
                    questionDoubleError1.setErrorCount(1);
                    questionDoubleError1.setAnswerCount(1);
                    merrorMapper.insert(questionDoubleError1);
                } else {
                    merror.setErrorCount(merror.getErrorCount() + 1);
                    merror.setAnswerCount(merror.getAnswerCount() + 1);
                    merrorMapper.updateById(merror);
                }
            }
        }

        List<JudgeQuestion> questionJudgeContent = content1.getJudgeQuestion();
        double questionJudgeScore = 0;
        for (int m = 0; m < questionJudgeContent.size(); m++) {
            Jerror jerror=jerrorMapper.selectOne(questionJudgeContent.get(m).getId());
            if (questionJudgeContent.get(m).getChooseAnswer() == null || questionJudgeContent.get(m).getChooseAnswer() == "") {
                questionJudgeScore = 0;
                if (jerror == null) {
                    Jerror questionJudgeError1 = new Jerror();
                    questionJudgeError1.setId(String.valueOf(System.currentTimeMillis()));
                    questionJudgeError1.setQuestionId(questionJudgeContent.get(m).getId());
                    questionJudgeError1.setSubjectId(questionJudgeContent.get(m).getSubjectId());
                    questionJudgeError1.setQuestionName(questionJudgeContent.get(m).getQuestionName());
                    questionJudgeError1.setErrorCount(1);
                    questionJudgeError1.setAnswerCount(1);
                    jerrorMapper.insert(questionJudgeError1);
                } else {
                    jerror.setErrorCount(jerror.getErrorCount() + 1);
                    jerror.setAnswerCount(jerror.getAnswerCount() + 1);
                    jerrorMapper.updateById(jerror);
                }
            }
           else if (questionJudgeContent.get(m).getChooseAnswer().equals(questionJudgeContent.get(m).getAnswer())) {
                questionJudgeScore += JudgeQuestion.score;
                if (jerror==null) {
                    Jerror questionJudgeError1=new Jerror();
                    questionJudgeError1.setAnswerCount(1);
                    jerrorMapper.updateById(questionJudgeError1);
                }else {
                    jerror.setAnswerCount(jerror.getAnswerCount()+1);
                    jerrorMapper.updateById(jerror);
                }
            }
            else {
                if (jerror==null){
                    Jerror questionJudgeError1=new Jerror();
                    questionJudgeError1.setId(String.valueOf(System.currentTimeMillis()));
                    questionJudgeError1.setQuestionId(questionJudgeContent.get(m).getId());
                    questionJudgeError1.setSubjectId(questionJudgeContent.get(m).getSubjectId());
                    questionJudgeError1.setQuestionName(questionJudgeContent.get(m).getQuestionName());
                    questionJudgeError1.setErrorCount(1);
                    questionJudgeError1.setAnswerCount(1);
                    jerrorMapper.insert(questionJudgeError1);
                }else {
                    jerror.setErrorCount(jerror.getErrorCount()+1);
                    jerror.setAnswerCount(jerror.getAnswerCount()+1);
                    jerrorMapper.updateById(jerror);
                }
            }
        }

        double totalScore = questionScore + questionsScore + questionJudgeScore;
        record.setContent(c);
        record.setQScore(String.valueOf(questionScore));
        record.setMScore(String.valueOf(questionsScore));
        record.setJScore(String.valueOf(questionJudgeScore));
        record.setScore(String.valueOf(totalScore));
        recordMapper.insert(record);
        result.put("code", 200);
        result.put("msg", "提交成功");
        result.put("data", record);
        return result;
    }

    public Map<String, Object> updatePassword(String studentId, String oldPassword, String password, String againPassword) {
        Map<String, Object> result = new HashMap<>();
        Student student = studentMapper.findByStudentId(studentId);
        if (student == null) {
            result.put("code", 400);
            result.put("msg", "修改密码失败，该学生不存在");
            return result;
        } else {
            if (!student.getPassword().equals(MD5Utils.encode(oldPassword))){
                result.put("code", 400);
                result.put("msg", "原始密码不正确,请重新输入");
                return result;
            }
            if (!password.equals(againPassword)) {
                result.put("code", 400);
                result.put("msg", "两次输入的密码不一致,请重新输入");
                return result;
            }
            Student stu = new Student();
            stu.setId(student.getId());
            stu.setStudentId(studentId);
            stu.setNickName(student.getNickName());
            stu.setClassId(student.getClassId());
            stu.setAge(student.getAge());
            stu.setGender(student.getGender());
            stu.setPassword(MD5Utils.encode(password));
            studentMapper.updateById(stu);
            result.put("code", 200);
            result.put("msg", "修改成功");
            return result;
        }
    }

    public List<Record> selectAllPage(Integer page, Integer limit, String searchName,String searchId) {

        PageHelper.startPage(page, limit);
        List<Record> recordList = recordMapper.selectAllPage(searchName,searchId);
        for (int i = 0; i < recordList.size(); i++) {
            String subjectId = recordList.get(i).getSubjectId();
            Subject subject = subjectMapper.selectById(subjectId);
            recordList.get(i).setSubjectName(subject.getSubjectName());
        }

        return recordList;
    }
}
