package com.exam.examservice.service;

import com.exam.examservice.beans.ManyQuestion;
import com.exam.examservice.beans.Subject;
import com.exam.examservice.mapper.ManyQuestionMapper;
import com.exam.examservice.mapper.SubjectMapper;
import com.exam.examservice.utils.ImportExecl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Service
public class ManyQuestionService {


    @Autowired
    private ManyQuestionMapper manyQuestionMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    public List<ManyQuestion> selectQuestionPage(Integer page, Integer limit, String searchName, String subjectId){
//        //第一条从哪里开始开始
//        Integer firstIndex=(page-1)*limit;
//        //最后一条到哪里结束
//        Integer lastIndex=page*limit;
        PageHelper.startPage(page,limit);
        List<ManyQuestion> questionList=manyQuestionMapper.selectPage(searchName,subjectId);
        for (int i = 0; i < questionList.size(); i++) {
            String subjectID = questionList.get(i).getSubjectId();
            Subject subject= subjectMapper.selectById(subjectID);
            questionList.get(i).setSubjectName(subject.getSubjectName());
        }
        return questionList;
    }

    public Map<String,Object> addQuestion(ManyQuestion manyQuestion){
        Map<String,Object> result=new HashMap<>();
        ManyQuestion q=manyQuestionMapper.selectByName(manyQuestion.getQuestionName());
        if (manyQuestion==null){
            result.put("code",400);
            result.put("msg","信息不能为空");
            return result;
        }
        try {
            if (q!=null){
                result.put("code",400);
                result.put("msg","该题目已存在");
                return result;
            }
            System.out.println(manyQuestion.getSubjectId());
            if (manyQuestion.getSubjectId()==null||manyQuestion.getSubjectId()==""|| "undefined".equals(manyQuestion.getSubjectId())){
                result.put("code",400);
                result.put("msg","请选择课程");
                return result;

            }
            if (q==null){
                ManyQuestion ques=new ManyQuestion();
                ques.setId(String.valueOf(System.currentTimeMillis()));
                ques.setQuestionName(manyQuestion.getQuestionName());
                ques.setSubjectId(manyQuestion.getSubjectId());
                ques.setQuestionA(manyQuestion.getQuestionA());
                ques.setQuestionB(manyQuestion.getQuestionB());
                ques.setQuestionC(manyQuestion.getQuestionC());
                ques.setQuestionD(manyQuestion.getQuestionD());
                ques.setAnswer(manyQuestion.getAnswer());
                manyQuestionMapper.insert(ques);
                result.put("code",200);
                result.put("msg","添加成功");
                return result;

            }

        }catch (Exception e){
            result.put("msg",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    public Map<String,Object> selectSubjectAll(){
        Map<String,Object> result=new HashMap<>();
        List<Subject> subjectList=subjectMapper.selectAll();
        result.put("code",200);
        result.put("data",subjectList);
        return result;
    }

    public Map<String,Object>selectById(String id){
        Map<String,Object> result=new HashMap<>();
        ManyQuestion question=manyQuestionMapper.selectById(id);
        if (question==null){
            result.put("code",400);
            result.put("msg","试题不存在");
            return result;
        }else {
            result.put("code",200);
            result.put("data",question);
            return result;
        }
    }

    public Map<String,Object>updataQuestion(ManyQuestion manyQuestion){
        Map<String,Object>result=new HashMap<>();
        manyQuestionMapper.updateById(manyQuestion);
        result.put("code",200);
        result.put("msg","修改成功");
        return result;
    }

    public Map<String,Object>deleteByIds(String[] ids){
        Map<String,Object> result=new HashMap<>();
        manyQuestionMapper.deleteByIds(ids);
        if (ids!=null&&ids.length>0) {
            result.put("code", 200);
            result.put("msg", "删除成功");
            manyQuestionMapper.deleteByIds(ids);
            return result;
        }else {
            result.put("code", 400);
            result.put("msg", "删除失败");
            return result;
        }
    }

    public Map<String,Object>addQuestionUpload(String filePath,HttpServletRequest request) throws  Exception {
        System.out.println("********************"+filePath);
        Map<String, Object> relust = new HashMap<>();
        ImportExecl poi = new ImportExecl();
        List<List<String>> list = poi.read(filePath);
        System.out.println("********************"+filePath);
        String subjectId=(String) request.getParameter("subjectId");
        System.out.println("---------------"+subjectId);
        List<ManyQuestion> questionList = new Vector<>();
        relust.put("data", questionList);

        if (subjectId==null||subjectId==""||subjectId.equals("undefined")){
            relust.put("code",400);
            relust.put("msg","请选择所属课程!");
            return relust;
        }

        if (list.size() < 1) {
            relust.put("code", 500);
            relust.put("msg", "所传模板不正确！");
            return relust;
        }
        List<String> cell = list.get(0);
        if (!"题目".equals(cell.get(0)) && !"选项A".equals(cell.get(1)) && !"选项B".equals(cell.get(2)) &&
                !"选项C".equals(cell.get(3)) && !"选项D".equals(cell.get(4))&& !"答案".equals(cell.get(5))) {
            relust.put("code", 500);
            relust.put("msg", "所传模板不正确！");
        }
        if (list != null) {
            for (int i=1;i<list.size();i++) {
                List<String>cellList=list.get(i);
                ManyQuestion question = new ManyQuestion();
                ManyQuestion q=manyQuestionMapper.selectByName(cellList.get(0));
                if (q!=null&&q.getQuestionName().equals(cellList.get(0))&&q.getQuestionA().equals(cellList.get(1))
                        &&q.getQuestionB().equals(cellList.get(2))&&q.getQuestionC().equals(cellList.get(3))
                        &&q.getQuestionD().equals(cellList.get(4))){
                    relust.put("code",202);
                    relust.put("msg","添加成功，有题目已存在！");
                    questionList.add(q);
                    continue;  //继续下个循环
                }
                question.setId(String.valueOf(System.currentTimeMillis()));
                question.setSubjectId(subjectId);
                question.setQuestionName(cellList.get(0));
                question.setQuestionA(cellList.get(1));
                question.setQuestionB(cellList.get(2));
                question.setQuestionC(cellList.get(3));
                question.setQuestionD(cellList.get(4));
                question.setAnswer(cellList.get(5));
                manyQuestionMapper.insert(question);
                relust.put("code",200);
                relust.put("msg","数据导入成功");

            }
        }
        return relust;
    }

}
