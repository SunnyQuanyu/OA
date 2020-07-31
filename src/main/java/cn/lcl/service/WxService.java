package cn.lcl.service;

import cn.lcl.pojo.Thing;
import cn.lcl.pojo.ThingReceiver;
import cn.lcl.pojo.result.Result;

public interface WxService {

    // 更新一个用户的微信openid信息
    // code是由前端传来的
    Result updateUserOpenid(String Code);

    // 定时获取微信的access_token
    void getWxToken();

    // 发送新的事务通知
    Boolean sendThingNote(String openid, Thing thing, ThingReceiver thingReceiver);

}
