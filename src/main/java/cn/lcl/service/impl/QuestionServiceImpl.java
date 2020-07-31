package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.QuestionAnswerMapper;
import cn.lcl.mapper.QuestionOptionMapper;
import cn.lcl.mapper.ThingQuestionMapper;
import cn.lcl.pojo.QuestionAnswer;
import cn.lcl.pojo.QuestionOption;
import cn.lcl.pojo.ThingQuestion;
import cn.lcl.pojo.User;
import cn.lcl.service.QuestionService;
import cn.lcl.util.AuthcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private ThingQuestionMapper thingQuestionMapper;
    @Autowired
    private QuestionOptionMapper questionOptionMapper;
    @Autowired
    private QuestionAnswerMapper questionAnswerMapper;


    @Override
    public Boolean saveQuestion(ThingQuestion question) {
        List<QuestionOption> options = question.getOptions();
        if (options == null || options.size() == 0) {
            throw new MyException(ResultEnum.THING_QUESTION_MUST_HAVE_OPTION);
        }
        if (question.getThingId() == null) {
            throw new MyException(ResultEnum.QUESTION_THING_ID_NOT_NULL);
        } else if (question.getReplyType() == null) {
            throw new MyException(ResultEnum.THING_QUESTION_TYPE_NOT_NULL);
        }

        thingQuestionMapper.insert(question);
        switch (question.getReplyType()) {
            case "1":
                // 单选
            case "2":
                // 多选
                for (QuestionOption option : options) {
                    option.setQuestionId(question.getId());
                    option.setTotalChoose(0);
                    questionOptionMapper.insert(option);
                }
                break;
            case "3":
                // 数字类型填空
                for (QuestionOption option : options) {
                    option.setQuestionId(question.getId());
                    option.setTotalScore((double) 0);
                    questionOptionMapper.insert(option);
                }
                break;
            case "4":
                for (QuestionOption option : options) {
                    option.setQuestionId(question.getId());
                    questionOptionMapper.insert(option);
                }
                break;
        }
        return true;
    }

    @Override
    public List<ThingQuestion> listQuestions(Integer thingId) {
        return thingQuestionMapper.selectQuestionListByThingId(thingId);
    }

    @Transactional
    @Override
    public Boolean saveQuestionAnswer(QuestionAnswer answer) {
        User user = AuthcUtil.getUser();
        answer.setUserId(user.getId());
        thingQuestionMapper.selectById(answer.getId());
        // 1.update the option
        QuestionOption option = questionOptionMapper.selectById(answer.getQuestionOptionId());
        switch (thingQuestionMapper.selectQuestionType(answer.getQuestionId())) {
            case "1":
                // 单选
            case "2":
                // 多选
                option.setTotalChoose(option.getTotalChoose() + 1);
                break;
            case "3":
                // 数字类型填空
                option.setTotalScore(option.getTotalScore() + answer.getScore());
                break;
            case "4":
                break;
        }
        questionOptionMapper.updateById(option);
        // 2.save the answer
        questionAnswerMapper.insert(answer);
        return true;
    }

    @Override
    public List<ThingQuestion> listQuestionsWithAnswers(Integer thingId) {
        User user = AuthcUtil.getUser();
        return thingQuestionMapper.selectQuestionAnswers(thingId,user.getId());
    }

}
