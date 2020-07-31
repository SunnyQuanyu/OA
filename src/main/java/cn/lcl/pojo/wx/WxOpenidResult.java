package cn.lcl.pojo.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxOpenidResult extends WxError {

    private String access_token;

    private Integer expires_in;

    private String refresh_token;

    private String openid;

    private String scope;

}
