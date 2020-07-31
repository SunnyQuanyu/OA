package cn.lcl.service;

import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.ThingAddDTO;
import cn.lcl.pojo.dto.ThingFinishDTO;
import cn.lcl.pojo.ThingReceiver;
import cn.lcl.pojo.result.Result;

public interface ThingService {

    //  插入一个事务
    Result saveThing(ThingAddDTO thing);

    //  获取所有创建的事务
    Result listCreatedThings(SearchPageDTO<?> page);

    //  获取一个创建的事务和对应的接受者的分页返回
    Result getCreatedThingAndReceivers(SearchPageDTO<ThingReceiver> page);

    //  获取一个参加的事务
    Result getJoinedThing(IdDTO thingId);

    //  阅读一个事务
    Result readThing(IdDTO thingId);

    //  获取所有参加的事务
    Result listJoinedThings(SearchPageDTO<ThingReceiver> page);

    //  完成一个事务
    Result finishThing(ThingFinishDTO finishDTO);

    //  获取一个已经完成的事务
    Result getFinishedThing(ThingReceiver thingReceiver);

    //  查看一个事务是否完成
    Result ifFinished(IdDTO idDTO);

}
