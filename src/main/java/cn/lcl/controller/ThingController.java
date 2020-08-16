package cn.lcl.controller;

import cn.lcl.pojo.Thing;
import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.ThingAddDTO;
import cn.lcl.pojo.dto.ThingFinishDTO;
import cn.lcl.pojo.ThingReceiver;
import cn.lcl.pojo.result.Result;
import cn.lcl.pojo.vo.ThingCreatedSearchVo;
import cn.lcl.service.ThingService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/thing")
public class ThingController {

    @Autowired
    ThingService thingService;


    @PostMapping
    public Result addThing(@Valid ThingAddDTO thing, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.saveThing(thing));
    }

    @PostMapping("/read")
    public Result readThing(@RequestBody @Valid IdDTO thingId, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.readThing(thingId));
    }

    @PostMapping("/joinedList")
    public Result joinedList(@RequestBody @Valid SearchPageDTO<ThingReceiver> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.listJoinedThings(page));
    }
    @PostMapping("/joinedList1")
    public Result joinedList1(@RequestBody @Valid SearchPageDTO<ThingCreatedSearchVo> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.listJoinedThings1(page));
    }

    @PostMapping("/createdList")
    public Result createdList(@RequestBody @Valid SearchPageDTO<Thing> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.listCreatedThings(page));
    }

    @PostMapping("/createdList1")
    public Result createdList1(@RequestBody @Valid SearchPageDTO<ThingCreatedSearchVo> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.listCreatedThings1(page));
    }

    @PostMapping("/deleteThing")
    public Result deleteThing(@RequestBody @Valid IdDTO thingId, BindingResult result){
        return ResultUtil.vaildFieldError(result, () -> thingService.deleteThing(thingId));
    }

    @PostMapping("/deleteJoinedThing")
    public Result deleteJoinedThing(@RequestBody @Valid IdDTO thingId, BindingResult result){
        return ResultUtil.vaildFieldError(result, () -> thingService.deleteJoinedThing(thingId));
    }
    /**
     *
     * @param page Page
     * @param result valid result
     * @return created thing and its receivers' page by search query.
     */
    @PostMapping("/created/get")
    public Result createdGet(@RequestBody @Valid SearchPageDTO<ThingReceiver> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.getCreatedThingAndReceivers(page));
    }


    /**
     * 事务接受者获取一个事务信息
     *
     * @param thingId
     * @return
     */
    @PostMapping("/joined/get")
    public Result joinedGet(@RequestBody @Valid IdDTO thingId, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.getJoinedThing(thingId));
    }

    @PostMapping("/finish")
    public Result finish(@Valid ThingFinishDTO finishDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.finishThing(finishDTO));
    }

    @PostMapping("/finished/get")
    public Result finishedGet(@RequestBody ThingReceiver thingReceiver) {
        return thingService.getFinishedThing(thingReceiver);
    }

    @PostMapping("/ifFinished")
    public Result ifFinished(@RequestBody @Valid IdDTO idDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.ifFinished(idDTO));
    }

    @PostMapping("/test")
    public void test(Date date) {
        System.out.println(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }
}
