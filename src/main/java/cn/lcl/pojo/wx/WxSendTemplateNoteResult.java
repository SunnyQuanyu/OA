package cn.lcl.pojo.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WxSendTemplateNoteResult extends WxError {
    public Double msgid;
}
