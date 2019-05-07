package com.exam.examservice.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Content implements Serializable {

    private List<Question> question;
    private List<ManyQuestion> manyQuestion;
    private List<JudgeQuestion> judgeQuestion;
}
