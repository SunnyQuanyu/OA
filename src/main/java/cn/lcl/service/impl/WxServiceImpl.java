package cn.lcl.service.impl;

import cn.lcl.config.wx.WxMappingJackson2HttpMessageConverter;
import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.ThingReceiverMapper;
import cn.lcl.mapper.UserMapper;
import cn.lcl.pojo.Thing;
import cn.lcl.pojo.ThingReceiver;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.pojo.wx.*;
import cn.lcl.service.WxService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.ResultUtil;
import cn.lcl.util.WxAccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class WxServiceImpl implements WxService {
    @Value("${wx.code2token-url}")
    private String code2tokenUrl;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.wx-token-url}")
    private String wxTokenUrl;
    @Value("${wx.template-url}")
    private String templateUrl;
    @Value("${wx.template_id}")
    private String template_id;
    @Value("${url}")
    private String url;


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ThingReceiverMapper thingReceiverMapper;


    @Override
    public Result updateUserOpenid(String code) {
        User user = AuthcUtil.getUser();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());

        HashMap<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("secret", secret);
        map.put("code", code);


        try {
            WxOpenidResult forObject = restTemplate.getForObject(code2tokenUrl, WxOpenidResult.class, map);
            if (null == forObject || !forObject.valid()) {
                throw new MyException(ResultEnum.USER_SAVE_OPENID_FAILED_IN_WX);
            }
            user.setWxOpenId(forObject.getOpenid());
            int i = userMapper.updateById(user);
            if (i != 1) {
                throw new MyException(ResultEnum.USER_SAVE_OPENID_FAILED_IN_DATABASE);
            }
        } catch (RestClientException e) {
            throw new MyException(ResultEnum.USER_SAVE_OPENID_FAILED_IN_WX);
        }

        return ResultUtil.success(user);

    }

    @Override
    public void getWxToken() {
        RestTemplate restTemplate = new RestTemplate();

        HashMap<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("secret", secret);

        try {
            WxTokenResult forObject = restTemplate.getForObject(wxTokenUrl, WxTokenResult.class, map);
            if (null == forObject) {
                throw new MyException(ResultEnum.GET_WX_TOKEN_FAILED);
            } else if (!forObject.valid()) {
                log.error(forObject.getErrmsg());
                throw new MyException(ResultEnum.GET_WX_TOKEN_FAILED);
            }
            WxAccessToken.setAccessToken(forObject.getAccess_token());
            log.info("access_token", forObject.getAccess_token());
        } catch (RestClientException e) {
            throw new MyException(ResultEnum.GET_WX_TOKEN_FAILED);
        }
    }

    @Override
    public Boolean sendThingNote(String openid, Thing thing, ThingReceiver thingReceiver) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> bodyParamsMap = new LinkedHashMap<>();
        bodyParamsMap.put("touser", openid);
        bodyParamsMap.put("template_id", template_id);
        bodyParamsMap.put("url", url + "thing/joined/" + thing.getId());
        System.out.println(url + "thing/joined/" + thing.getId());
        WxSendTemplateNoteData data = new WxSendTemplateNoteData();
        data.setFirst(new WxSendTemplateNoteField("您有新的事务，点击查看！"));
        data.setKeyword1(new WxSendTemplateNoteField(thing.getTitle()));
        if (thing.getStartTime() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            data.setKeyword2(new WxSendTemplateNoteField(simpleDateFormat.format(thing.getStartTime())));
        }
        data.setKeyword3(new WxSendTemplateNoteField("无"));
        data.setKeyword4(new WxSendTemplateNoteField(thing.getRealName()));
        data.setRemark(new WxSendTemplateNoteField(thing.getContent()));
        bodyParamsMap.put("data", data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(bodyParamsMap, headers);


        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("access_token", WxAccessToken.getAccessToken());

        String hasSendNote = "0";

        try {
            WxSendTemplateNoteResult forObject = restTemplate.postForObject(templateUrl, request, WxSendTemplateNoteResult.class, paramsMap);
            if (null == forObject) {
                log.error(ResultEnum.SEND_WX_NOTE_FAILED.getMsg());
            } else if (!forObject.valid()) {
                log.error(forObject.getErrcode().toString());
                log.error(forObject.getErrmsg());
                hasSendNote = "1";
            }
        } catch (RestClientException e) {
            throw new MyException(ResultEnum.SEND_WX_NOTE_FAILED_IN_HTTP);
        }

        thingReceiver.setHasSendNote(hasSendNote);
        thingReceiverMapper.insert(thingReceiver);

        return true;
    }
}
