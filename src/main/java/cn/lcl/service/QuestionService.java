package cn.lcl.service;

import cn.lcl.pojo.QuestionAnswer;
import cn.lcl.pojo.ThingQuestion;

import java.util.List;

public interface QuestionService {

    /**
     *
     * @param question a question
     * @return save success or failed
     */
    Boolean saveQuestion(ThingQuestion question);

    // 获取所有问题信息
    List<ThingQuestion> listQuestions(Integer thingId);

    /**
     *
     * @param answer one answer-option of question.
     * @return save success or failed
     */
    Boolean saveQuestionAnswer(QuestionAnswer answer);

    // 获取某个事务对应的所有问题和答案
    List<ThingQuestion> listQuestionsWithAnswers(Integer thingId);

}
