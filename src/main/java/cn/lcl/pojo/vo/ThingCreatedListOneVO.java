package cn.lcl.pojo.vo;

import cn.lcl.pojo.Thing;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * one result of the created thing list.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ThingCreatedListOneVO extends Thing {

    private Integer receiversCount;

    private Integer readCount;

    private Integer finishedCount;


}
