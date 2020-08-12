package cn.lcl.pojo.vo;

import cn.lcl.pojo.Thing;
import lombok.Data;

/**
 * @author zhenwei
 * @date 2020/8/11
 */
@Data
public class ThingCreatedSearchVo extends Thing {

    private Integer tagId;

    private Integer isRead;

    private Integer isFinished;
}
