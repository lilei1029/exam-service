package com.exam.examservice.controller;

import com.exam.examservice.beans.ManyQuestion;
import com.exam.examservice.mapper.ManyQuestionMapper;
import com.exam.examservice.service.ManyQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author LILEI
 */
@RestController
@RequestMapping("/manyQuestion")
public class ManyQuestionController {

    @Autowired
    private ManyQuestionService manyQuestionService;

    @Autowired
    private ManyQuestionMapper manyQuestionMapper;

    @Value("${file.path}")
    private String filePath;

    @RequestMapping("/selectQuestionPage")
    public Map<String,Object> selectQuestionPage(Integer page, Integer limit
            , @RequestParam(value = "searchName",required = false) String searchName
            , @RequestParam(value = "subjectId",required = false) String subjectId){
        Map<String,Object> result=new HashMap<>();
        List<ManyQuestion> questionList=manyQuestionService.selectQuestionPage(page,limit,searchName,subjectId);
        Integer count=manyQuestionMapper.selectCount();
        result.put("code",200);
        result.put("count",count);
        result.put("data",questionList);
        return result;
    }

    @RequestMapping("/selectSubjectAll")
    public Map<String,Object>selectSubjectAll(){
        return manyQuestionService.selectSubjectAll();
    }

    @RequestMapping("/addQuestion")
    public Map<String,Object> addQuestion(ManyQuestion question){
        return manyQuestionService.addQuestion(question);
    }

    @RequestMapping("/selectById")
    public Map<String,Object>selectById(String id){
        return manyQuestionService.selectById(id);
    }

    @RequestMapping("/updateQuestion")
    public Map<String,Object>updateQuestion(ManyQuestion question){
        return manyQuestionService.updataQuestion(question);
    }

    @RequestMapping("/deleteByIds")
    public Map<String,Object>deleteByIds(String[] ids){
        return manyQuestionService.deleteByIds(ids);
    }

    @RequestMapping("/addQuestionUpload")
    public Map<String,Object>addQuestionUpload(@RequestParam(value = "file") MultipartFile multipartFile, HttpServletRequest request) throws  Exception{
        Map<String,Object>result=new HashMap<>();
        String subjectName = (String)request.getParameter("subjectId");
        System.out.println("++++++++============"+subjectName);
        String uploadFilePath=multipartFile.getOriginalFilename();
        //截取上传文件后缀
        String uploadFileSuffix=uploadFilePath.substring(uploadFilePath.indexOf('.')+1,uploadFilePath.length());
        String fileName= UUID.randomUUID()+"."+uploadFileSuffix;

        //将文件放入磁盘读取
        try {
            File dest = new File(filePath +File.separator+ fileName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            multipartFile.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return manyQuestionService.addQuestionUpload(filePath+File.separator+fileName, request);
    }
}
