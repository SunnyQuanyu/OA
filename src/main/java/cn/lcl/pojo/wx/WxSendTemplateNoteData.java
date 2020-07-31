package cn.lcl.pojo.wx;

import lombok.Data;

@Data
public class WxSendTemplateNoteData {

    // 首部
    public WxSendTemplateNoteField first;

    // 日程名称
    public WxSendTemplateNoteField keyword1;

    // 时间
    public WxSendTemplateNoteField keyword2;

    // 地点
    public WxSendTemplateNoteField keyword3;

    // 相关人
    public WxSendTemplateNoteField keyword4;

    // 备注
    public WxSendTemplateNoteField remark;

}
