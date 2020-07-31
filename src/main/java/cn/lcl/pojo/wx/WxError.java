package cn.lcl.pojo.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxError {

    private Integer errcode;

    private String errmsg;

    public boolean valid(){
        return errcode == null || errcode == 0;
    }
}
