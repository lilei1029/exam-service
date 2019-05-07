package com.exam.examservice.task;

import com.exam.examservice.beans.Exam;
import com.exam.examservice.mapper.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectTask {

    @Autowired
    private ExamMapper examMapper;

    @Scheduled(initialDelay = 10000, fixedDelay = 15000)
    public void subjectTime() {
        try {
            List<Exam> examList = examMapper.findEndTimeLessSysdate();
            if (examList != null && examList.size() > 0) {
                for (Exam exam : examList) {
                    Exam e = new Exam();
                    e.setId(exam.getId());
                    e.setState("0");
                    System.out.println("**************");
                    examMapper.updateById(e);
                }
            }

        } catch (Exception e) {

        }
    }


}
