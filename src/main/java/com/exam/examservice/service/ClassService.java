package com.exam.examservice.service;

import com.exam.examservice.beans.Class;
import com.exam.examservice.mapper.ClassMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exam.examservice.utils.IdGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassService {

    @Autowired
    private ClassMapper classMapper;

    private IdGenerator idWorker = new IdGenerator(0, 0);;



    public List<Class> selectClassPage(Integer page, Integer limit, String searchName){
//        //第一条从哪里开始开始
//        Integer firstIndex=(page-1)*limit;
//        //最后一条到哪里结束
//        Integer lastIndex=page*limit;
        PageHelper.startPage(page,limit);
        List<Class> classList=classMapper.selectPage(searchName);
        return classList;
    }

    public Map<String,Object> addClass(Class record){
        Map<String,Object> result=new HashMap<>();
        if (record==null){
            result.put("code",400);
            result.put("msg","参数不能为空");
            return result;
        }else {
            Class c = classMapper.selectByName(record.getClassName());
            if (c!=null){
                result.put("code",404);
                result.put("msg","班级已经存在!");
                return result;
            }else {
                Class cs=new Class();
//                c.setId(String.valueOf(idWorker.nextId()));
                cs.setId(String.valueOf(System.currentTimeMillis()));
                cs.setClassName(record.getClassName());
                try {
                    classMapper.insert(cs);
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
        Class c=classMapper.selectById(id);
        if (c==null){
            result.put("code",400);
            result.put("msg","班级不存在");
            return result;
        }else {
            result.put("code",200);
            result.put("data",c);
            return result;
        }

    }

    public Map<String,Object>updateClass(Class cs){
        Map<String,Object> result=new HashMap<>();
        if (cs!=null){
            Class c=classMapper.selectById(cs.getId());
            c.setId(cs.getId());
            c.setClassName(cs.getClassName());
            classMapper.updateById(c);
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
            classMapper.deleteByIds(ids);
        }else {
            result.put("code",400);
            result.put("msg","删除失败");
        }
        return result;
    }

}
