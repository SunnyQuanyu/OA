package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.*;
import cn.lcl.pojo.*;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.ThingAddDTO;
import cn.lcl.pojo.dto.ThingFinishDTO;
import cn.lcl.pojo.result.Result;
import cn.lcl.pojo.vo.*;
import cn.lcl.service.QuestionService;
import cn.lcl.service.ThingService;
import cn.lcl.service.WxService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.FieldStringUtil;
import cn.lcl.util.FileUtil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class ThingServiceImpl implements ThingService {

    private final ThingMapper thingMapper;
    private final ThingTagMapper thingTagMapper;
    private final ThingTeamMapper thingTeamMapper;
    private final UserMapper userMapper;
    private final ThingReceiverMapper thingReceiverMapper;
    private final ThingSendFileMapper thingSendFileMapper;
    private final ThingReplyFileMapper thingReplyFileMapper;
    private final QuestionService questionService;
    private final TeamMemberMapper teamMemberMapper;
    private final WxService wxService;


    public ThingServiceImpl(ThingMapper thingMapper, ThingTagMapper thingTagMapper,ThingTeamMapper thingTeamMapper,
                            ThingReceiverMapper thingReceiverMapper, ThingSendFileMapper thingSendFileMapper,
                            ThingReplyFileMapper thingReplyFileMapper, QuestionService questionService, TeamMemberMapper teamMemberMapper, WxService wxService, UserMapper userMapper) {
        this.thingMapper = thingMapper;
        this.thingTagMapper = thingTagMapper;
        this.thingTeamMapper = thingTeamMapper;
        this.thingReceiverMapper = thingReceiverMapper;
        this.thingSendFileMapper = thingSendFileMapper;
        this.thingReplyFileMapper = thingReplyFileMapper;
        this.questionService = questionService;
        this.teamMemberMapper = teamMemberMapper;
        this.wxService = wxService;
        this.userMapper = userMapper;
    }


    @Transactional
    @Override
    public Result saveThing(ThingAddDTO thing) {
        // 1.插入事务
        User sender = AuthcUtil.getUser();
        thing.setRealName(sender.getRealName());
        thingMapper.insert(thing);
        // 2.插入 事务-标签对应关系


        if (thing.getTagId() == null || thing.getTagId().length == 0) {
            throw new MyException(ResultEnum.THING_RECEIVERIDS_NOT_NULL);
        }
        for (String tagId : thing.getTagId()) {
            // danger 判断receiver为空？
            ThingTag thingTag = new ThingTag();

            thingTag.setThingId(thing.getId());
            thingTag.setTagId(tagId);


            thingTagMapper.insert(thingTag);
        }
        System.out.print(thing.getTagId());

        //6.插入事务与小组的对应关系
        for (String teamId : thing.getTeamId()) {
            // danger 判断receiver为空？
            ThingTeam thingTeam = new ThingTeam();

            thingTeam.setThingId(thing.getId());
            thingTeam.setTeamId(teamId);


            thingTeamMapper.insert(thingTeam);
        }

        // 3.插入 事务-用户对应关系

        List<Integer> users = new ArrayList<>();
        for (String teamId : thing.getTeamId()) {
            List<User> teamUsers = teamMemberMapper.selectUsersByTeamId(teamId);
            for (User user : teamUsers) {
                if (!users.contains(user.getId())) {
                    users.add(user.getId());
                }
            }
        }


        for (Integer i = 0; i < users.size(); i++) {
            for (Integer receiverId : thing.getReceiverIds()) {
                if (!users.contains(receiverId)) {
                    users.add(receiverId);
                }
            }
        }

        long startTime = System.currentTimeMillis();// 获取开始时间
        //           ExecutorService executorService = Executors.newFixedThreadPool(teamUsers.size());
        List<Future<Boolean>> futures = new ArrayList<>();

        ThingReceiver thingReceiver = new ThingReceiver();
        for (Integer j : users) {
            thingReceiver.setHasRead("0");
            thingReceiver.setHasFinished("0");
            thingReceiver.setHasSendNote("0");
            thingReceiver.setThingId(thing.getId());
            thingReceiver.setUserId(j);
            thingReceiverMapper.insert(thingReceiver);
        }



    /*                if (user.getWxOpenId() != null) {
    //                    futures.add(executorService.submit(() -> wxService.sendThingNote(user.getWxOpenId(), thing, thingReceiver)));
                    } else {
                        thingReceiverMapper.insert(thingReceiver);
                    }*/


        for (Future<Boolean> future : futures) {
            try {
                future.get(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        //            executorService.shutdown();
        long endTime = System.currentTimeMillis();// 获取结束时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");



   /*     else {
            if (thing.getReceiverIds() == null || thing.getReceiverIds().length == 0) {
                throw new MyException(ResultEnum.THING_RECEIVERIDS_NOT_NULL);
            }



            for (Integer receiverId : thing.getReceiverIds()) {
                // danger 判断receiver为空？
                ThingReceiver thingReceiver = new ThingReceiver();
                thingReceiver.setHasRead("0");
                thingReceiver.setHasFinished("0");
                thingReceiver.setHasSendNote("0");
                thingReceiver.setThingId(thing.getId());

                thingReceiver.setUserId(receiverId);
                User user = userMapper.selectById(receiverId);
                if (user.getWxOpenId() != null) {
                    if (wxService.sendThingNote(user.getWxOpenId(), thing, thingReceiver)) {
                        thingReceiver.setHasSendNote("1");
                    }
                }
                thingReceiverMapper.insert(thingReceiver);
            }
        }*/
        // 4.插入 事务-文件对应关系
        if ("1".equals(thing.getHasSendFile())) {
            for (MultipartFile file : thing.getFiles()) {
                if ("".equals(file.getOriginalFilename())) {
                    continue;
                }
                ThingSendFile thingSendFile = new ThingSendFile();
                thingSendFile.setThingId(thing.getId());
                thingSendFile.setOriginName(file.getOriginalFilename());
                try {
                    thingSendFile.setFileUrl(FileUtil.upload(file, String.valueOf(thing.getId())));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new MyException(ResultEnum.FILE_UPLOAD_FAILED);
                }
                thingSendFileMapper.insert(thingSendFile);
            }
        }
        // 5.插入 事务-问题对应关系(单独业务)
        // 如果不需要完成，则不需要此关系。
        if (!"1".equals(thing.getNeedFinish())) {
            return ResultUtil.success();
        }
        if ("1".equals(thing.getNeedAnswer())) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(thing.getQuestionsJSON());
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        ThingQuestion thingQuestion = mapper.readValue(node.toString(), ThingQuestion.class);
                        thingQuestion.setThingId(thing.getId());
                        Boolean aBoolean = questionService.saveQuestion(thingQuestion);
                        if (!aBoolean) {
                            throw new MyException(ResultEnum.THING_QUESTION_INSERT_FAILED);
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new MyException(ResultEnum.THING_QUESTION_INSERT_FAILED);
            }
        }

        return ResultUtil.success();
    }

    @Override
    public Result listCreatedThings(SearchPageDTO<?> page) {
        User user = AuthcUtil.getUser();
        Page<ThingCreatedListOneVO> paramPage = page.getParamPage();
        Page<ThingCreatedListOneVO> things1 = thingMapper.getCreatedThingsExpectTag(paramPage, user.getId());
        for (ThingCreatedListOneVO things2 : things1.getRecords()) {
            List<Tag> Tags = thingMapper.getTagByThingId(things2.getId());
            String tagName = "";
            for (Tag getTags : Tags) {
                tagName = tagName + getTags.getTagName() + ",";
            }
            tagName = tagName.substring(0, tagName.length() - 1);
            things2.setTagName(tagName);
        }
        return ResultUtil.success(things1);
      /*  Thing thing = page.getData();
        QueryWrapper<Thing> query = Wrappers.query();
        Map<String, String> map = null;
        try{
            map = BeanUtils.describe(thing);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    query.like(FieldStringUtil.HumpToUnderline(entry.getKey()), entry.getValue());
                }
            }
            return ResultUtil.success(thingMapper.selectPage(
                    new Page<>(page.getPageCurrent(), page.getPageSize()), query));
        }catch(Exception e){
            e.printStackTrace();
            throw new MyException(ResultEnum.USERS_FIND_ERROR);
        }*/
    }

    @Override
    public Result getCreatedThingAndReceivers(SearchPageDTO<ThingReceiver> page) {
        ThingReceiver tr = page.getData();
        Thing thing = thingMapper.getThingById11(tr.getThingId());
        if (thing == null) {
            throw new MyException(ResultEnum.THING_NOT_FOUND);
        }
        // 1. valid the thing not null.
        // 2. get vo from mapper and set its thing field.

        ThingCreatedVO thingCreatedVO = thingMapper.getCreatedThingAboutReceiverNum(thing.getId());
        // 3.4. get the files, questions
        getCommonThingVO(thing, thingCreatedVO);
        // 5. get the receivers page.
        Page<ThingReceiver> paramPage = page.getParamPage();
        thingCreatedVO.setThingReceiversPage(
                thingReceiverMapper.selectThingReceiversAndUserRealNamePageByThingId(paramPage, tr));
        System.out.println(thingCreatedVO);

        List<Tag> tags = thingMapper.getTagByThingId(thingCreatedVO.getThing().getId());
        String tagName22 = "";
        for (Tag tag1 : tags) {
            tagName22 = tagName22 + tag1.getTagName() + ",";
        }
        tagName22 = tagName22.substring(0, tagName22.length() - 1);
        thingCreatedVO.getThing().setTagName(tagName22);

        return ResultUtil.success(thingCreatedVO);
    }

    @Override
    public Result getJoinedThing(IdDTO thingId) {
        Thing thing = thingMapper.getThingById11(thingId.getId());

        ThingReceiver thingReceiver = checkThingAndGetThingReceiver(thing);
        List<Tag> Tags = thingReceiverMapper.getTagByReceiverId(thingReceiver.getUserId(), thingReceiver.getThingId());
        thingReceiver.setHasRead("1");

        String tagName1 = "";
        for (Tag getTags : Tags) {


            tagName1 = tagName1 + getTags.getTagName() + ",";
            System.out.println(tagName1);

        }
        tagName1 = tagName1.substring(0, tagName1.length() - 1);
        thingReceiver.setTagName(tagName1);

        System.out.println(thingReceiver.getTagName());

        thingReceiverMapper.updateById(thingReceiver);
        thing.setTagName(tagName1);
        // 1. get the thing entity.
        System.out.println(thing.getTagName());
        ThingJoinedVO thingJoinedVO = new ThingJoinedVO();
        // 2. get others.
        getCommonThingVO(thing, thingJoinedVO);

        return ResultUtil.success(thingJoinedVO);
    }

    @Override
    public Result readThing(IdDTO thingId) {
        User user = AuthcUtil.getUser();
        ThingReceiver one = new LambdaQueryChainWrapper<>(thingReceiverMapper)
                .eq(ThingReceiver::getThingId, thingId.getId())
                .eq(ThingReceiver::getUserId, user.getId())
                .one();
        one.setHasRead("1");
        thingReceiverMapper.updateById(one);
        return ResultUtil.success(one);
    }

    @Override
    public Result deleteThing(IdDTO thingId) {

        return ResultUtil.success(thingMapper.deleteThingById(thingId.getId()));
    }

    @Override
    public Result deleteJoinedThing(IdDTO thingId) {
        User user = AuthcUtil.getUser();
        return ResultUtil.success(thingReceiverMapper.deleteThingByUserId(thingId.getId(),user.getId()));
    }

    @Override
    public Result listJoinedThings(SearchPageDTO<ThingReceiver> page) {
        User user = AuthcUtil.getUser();
        Page<ThingReceiver> paramPage = page.getParamPage();

    /*    Page<ThingReceiver> receiversThings = thingReceiverMapper.selectThingReceiversByReceiverId(
                paramPage, user.getId(), page.getData());*/

        Page<ThingReceiver> receiversThings1 = thingReceiverMapper.selectThingReceiversByReceiverIdExceptTag(
                paramPage, user.getId(), page.getData());

        for (ThingReceiver receiversThings2 : receiversThings1.getRecords()) {
            List<Tag> Tags = thingReceiverMapper.getTagByReceiverId(user.getId(), receiversThings2.getThingId());
            String tagName1 = "";
            for (Tag getTags : Tags) {
                System.out.println(getTags.getTagName());
                tagName1 = tagName1 + getTags.getTagName() + ",";
                System.out.println(tagName1);
            }
            tagName1 = tagName1.substring(0, tagName1.length() - 1);
            receiversThings2.setTagName(tagName1);
            System.out.println(tagName1);
        }

        return ResultUtil.success(receiversThings1);
    }

    @Override
    public Result teamThing(IdDTO teamId) {
        System.out.println(teamId.getId());
        List<ThingCreatedListOneVO> things = thingMapper.getTeamThingByTeamId(teamId.getId());

        for (ThingCreatedListOneVO things1 : things) {
            List<Tag> Tags = thingMapper.getTagByThingId(things1.getId());
            String tagName1 = "";
            for (Tag getTags : Tags) {
                System.out.println(getTags.getTagName());
                tagName1 = tagName1 + getTags.getTagName() + ",";
                System.out.println(tagName1);
            }
            tagName1 = tagName1.substring(0, tagName1.length() - 1);
            things1.setTagName(tagName1);
            System.out.println(tagName1);
        }
        return ResultUtil.success(things);
    }


    @Transactional
    @Override
    public Result finishThing(ThingFinishDTO finishDTO) {
        Thing thing = thingMapper.selectById(finishDTO.getThingId());
        ThingReceiver thingReceiver = checkThingAndGetThingReceiver(thing);
        if ("1".equals(thingReceiver.getHasFinished())) {
            throw new MyException(ResultEnum.THING_HAS_FINISHED);
        }
        if (!"1".equals(thing.getNeedFinish())) {
            throw new MyException(ResultEnum.THING_NOT_HAVE_TO_FINISH);
        }
        // finish must be read
        thingReceiver.setHasRead("1");

        // 1. the note of this reply
        if (finishDTO.getContent() != null) {
            thingReceiver.setContent(finishDTO.getContent());
        }
        // 2.如果需要回执文件
        if ("1".equals(thing.getNeedFileReply())) {
            finishFiles(finishDTO, thing);
        }
        // 3.如果需要回执answer
        if ("1".equals(thing.getNeedAnswer())) {
            finishAnswers(finishDTO, thing);
        }
        // 4.成功后设为已完成
        thingReceiver.setHasFinished("1");
        thingReceiverMapper.updateById(thingReceiver);
        return ResultUtil.success();
    }

    @Override
    public Result getFinishedThing(ThingReceiver thingReceiver) {
        // 1. test if exist.
        thingReceiver = thingReceiverMapper.
                selectThingReceiverAndUserRealNamePage(thingReceiver.getThingId(), thingReceiver.getUserId());
        if (thingReceiver == null) {
            throw new MyException(ResultEnum.THING_AND_RECEIVER_NOT_FOUND);
        }
        if (!"1".equals(thingReceiver.getHasFinished())) {
            throw new MyException(ResultEnum.THING_NOT_FINISHED);
        }

        // 2. get the content in ThingReceiver
        ThingFInishedVO thingFinishedVO = new ThingFInishedVO();
        thingFinishedVO.setThingReceiver(thingReceiver);

        // 3. get the reply files
        Thing thing = thingMapper.selectById(thingReceiver.getThingId());
        if ("1".equals(thing.getNeedFileReply())) {
            List<ThingReplyFile> list = new LambdaQueryChainWrapper<>(thingReplyFileMapper)
                    .eq(ThingReplyFile::getThingId, thingReceiver.getThingId())
                    .eq(ThingReplyFile::getUserId, thingReceiver.getUserId())
                    .list();
            thingFinishedVO.setFiles(list);
        }

        // 4. get the answer
        if ("1".equals(thing.getNeedAnswer())) {
            thingFinishedVO.setQuestions(questionService.listQuestionsWithAnswers(thing.getId()));
        }
        return ResultUtil.success(thingFinishedVO);
    }

    @Override
    public Result ifFinished(IdDTO idDTO) {
        User user = AuthcUtil.getUser();
        ThingReceiver one = new LambdaQueryChainWrapper<>(thingReceiverMapper)
                .eq(ThingReceiver::getThingId, idDTO.getId())
                .eq(ThingReceiver::getUserId, user.getId())
                .one();
        return ResultUtil.success("1".equals(one.getHasFinished()));
    }

    private ThingReceiver checkThingAndGetThingReceiver(Thing thing) {
        if (thing == null) {
            throw new MyException(ResultEnum.THING_NOT_FOUND);
        }
        User user = AuthcUtil.getUser();
        return new LambdaQueryChainWrapper<>(thingReceiverMapper)
                .eq(ThingReceiver::getThingId, thing.getId())
                .eq(ThingReceiver::getUserId, user.getId())
                .one();
    }

    private void finishAnswers(ThingFinishDTO finishDTO, Thing thing) {
        if ("1".equals(thing.getNeedAnswer())) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(finishDTO.getAnswersJSON());
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        QuestionAnswer answer = mapper.readValue(node.toString(), QuestionAnswer.class);
                        Boolean aBoolean = questionService.saveQuestionAnswer(answer);
                        if (!aBoolean) {
                            throw new MyException(ResultEnum.ANSWER_INSERT_FAILED);
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new MyException(ResultEnum.ANSWER_INSERT_FAILED);
            }
        }
    }

    private void finishFiles(ThingFinishDTO finishDTO, Thing thing) {
        if ("1".equals(thing.getNeedFileReply())) {
            for (MultipartFile file : finishDTO.getFiles()) {
                if ("".equals(file.getOriginalFilename())) {
                    continue;
                }
                ThingReplyFile thingReplyFile = new ThingReplyFile();
                thingReplyFile.setThingId(thing.getId());
                thingReplyFile.setOriginName(file.getOriginalFilename());
                User user = AuthcUtil.getUser();
                try {
                    thingReplyFile.setFileUrl(FileUtil.upload(file, thing.getId() + "/" + user.getId()));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new MyException(ResultEnum.FILE_UPLOAD_FAILED);
                }
                thingReplyFile.setUserId(user.getId());
                thingReplyFileMapper.insert(thingReplyFile);
            }
        }
    }

    private void getCommonThingVO(Thing thing, ThingJoinedVO vo) {
        vo.setThing(thing);
        // 1. if thing has files, get the files.
        if ("1".equals(thing.getHasSendFile())) {
            vo.setFiles(new LambdaQueryChainWrapper<>(thingSendFileMapper)
                    .eq(ThingSendFile::getThingId, thing.getId())
                    .list());
        }
        // 2. get questions
        if ("1".equals(thing.getNeedAnswer())) {
            vo.setQuestions(questionService.listQuestions(thing.getId()));
        }
    }

    @Override
    public Result listCreatedThings1(SearchPageDTO<ThingCreatedSearchVo> page) {
        User user = AuthcUtil.getUser();
        Page<ThingCreatedSearchVo> paramPage = page.getParamPage();
        ThingCreatedSearchVo data = page.getData();


        List<ThingCreatedListOneVO> thing = thingMapper.getCreatedThings(data, user.getId());


        if (data.getIsRead() != null) {
            Iterator<ThingCreatedListOneVO> iterator = thing.iterator();
            while (iterator.hasNext()) {
                ThingCreatedListOneVO vo = iterator.next();
                if (data.getIsRead() == 0 && vo.getReadCount() != 0) {//无人阅读
                    iterator.remove();
                } else if (data.getIsRead() == 1 &&
                        (vo.getReadCount().equals(vo.getReceiversCount()) || vo.getReadCount().equals(0))) {//部分阅读
                    iterator.remove();
                } else if (data.getIsRead() == 2 && !vo.getReadCount().equals(vo.getReceiversCount())) {//全阅读
                    iterator.remove();
                }
            }
        }


            if (data.getIsFinished() != null) {
                    Iterator<ThingCreatedListOneVO> iterator = thing.iterator();
                    while (iterator.hasNext()) {
                        ThingCreatedListOneVO vo = iterator.next();
                        if (data.getIsFinished() == 0 && vo.getFinishedCount() != 0) {//无人完成
                            iterator.remove();
                        } else if (data.getIsFinished() == 1 &&
                                (vo.getFinishedCount().equals(vo.getReceiversCount()) || vo.getFinishedCount().equals(0))) {//部分人完成
                            iterator.remove();
                        } else if (data.getIsFinished() == 2 && !vo.getFinishedCount().equals(vo.getReceiversCount())) {//全部完成
                            iterator.remove();
                        }
                    }
            }


        if (data.getTagId() != null) {
            Iterator<ThingCreatedListOneVO> iterator = thing.iterator();
            while (iterator.hasNext()) {
                ThingCreatedListOneVO vo = iterator.next();
                List<Tag> tags = thingMapper.getTagByThingId(vo.getId());
                boolean hasTag = false;
                for (Tag tag : tags) {
                    if (tag.getId().equals(data.getTagId())) {
                        hasTag = true;
                    }
                }

                if (!hasTag) {
                    iterator.remove();
                }
            }
        }

        for (ThingCreatedListOneVO vo : thing) {
            List<Tag> tags = thingMapper.getTagByThingId(vo.getId());
            String tagName = "";
            for (Tag getTags : tags) {
                tagName = tagName + getTags.getTagName() + ",";
            }
            tagName = tagName.substring(0, tagName.length() - 1);
            vo.setTagName(tagName);
        }

        Page<ThingCreatedListOneVO> thing1 = new Page<>();
        thing1.setCurrent(paramPage.getCurrent());
        thing1.setSize(paramPage.getSize());
        thing1.setTotal(thing.size());
        if (paramPage.getCurrent() >= 1) {
            thing1.setRecords(thing.subList((int) ((paramPage.getCurrent() - 1) * paramPage.getSize()),
                    (int) (paramPage.getCurrent() * paramPage.getSize()<=thing.size()?
                            paramPage.getCurrent() * paramPage.getSize():thing.size())));
        } else {
            thing1.setRecords(null);
        }

        return ResultUtil.success(thing1);
    }


    @Override
    public Result listJoinedThings1(SearchPageDTO<ThingCreatedSearchVo> page) {
        User user = AuthcUtil.getUser();
        Page<ThingCreatedSearchVo> paramPage = page.getParamPage();
        ThingCreatedSearchVo data = page.getData();
        System.out.println(data);

        List<ThingCreatedListOneVO> thing = thingMapper.getJoinedThings(data, user.getId());


    /*    if (data.getIsRead() != null) {
            Iterator<ThingCreatedListOneVO> iterator = thing.iterator();
            while (iterator.hasNext()) {
                ThingCreatedListOneVO vo = iterator.next();
                System.out.println(vo);
                if (data.getIsRead() == 0) {//未阅读
                    iterator.remove();
                } else if (data.getIsRead() == 1) {//已阅读
                    iterator.remove();
                }
            }
        }


        if (data.getIsFinished() != null) {
            Iterator<ThingCreatedListOneVO> iterator = thing.iterator();
            while (iterator.hasNext()) {
                ThingCreatedListOneVO vo = iterator.next();
                if (data.getIsFinished() == 0) {//未完成
                    iterator.remove();
                } else if (data.getIsFinished() == 1) {//已完成
                    iterator.remove();
                }
            }
        }*/


        if (data.getTagId() != null) {
            Iterator<ThingCreatedListOneVO> iterator = thing.iterator();
            while (iterator.hasNext()) {
                ThingCreatedListOneVO vo = iterator.next();
                List<Tag> tags = thingMapper.getTagByThingId(vo.getId());
                boolean hasTag = false;
                for (Tag tag : tags) {
                    if (tag.getId().equals(data.getTagId())) {
                        hasTag = true;
                    }
                }

                if (!hasTag) {
                    iterator.remove();
                }
            }
        }

        for (ThingCreatedListOneVO vo : thing) {
            List<Tag> tags = thingMapper.getTagByThingId(vo.getId());
            String tagName = "";
            for (Tag getTags : tags) {
                tagName = tagName + getTags.getTagName() + ",";
            }
            tagName = tagName.substring(0, tagName.length() - 1);
            vo.setTagName(tagName);
        }

        Page<ThingCreatedListOneVO> thing1 = new Page<>();
        thing1.setCurrent(paramPage.getCurrent());
        thing1.setSize(paramPage.getSize());
        thing1.setTotal(thing.size());
        if (paramPage.getCurrent() >= 1) {
            thing1.setRecords(thing.subList((int) ((paramPage.getCurrent() - 1) * paramPage.getSize()),
                    (int) (paramPage.getCurrent() * paramPage.getSize()<=thing.size()?
                            paramPage.getCurrent() * paramPage.getSize():thing.size())));
        } else {
            thing1.setRecords(null);
        }

        return ResultUtil.success(thing1);
    }

}
