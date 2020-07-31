package cn.lcl.pojo.vo;

import cn.lcl.pojo.ThingReceiver;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class ThingCreatedVO extends ThingJoinedVO {

    private Integer receiversCount;

    private Integer readCount;

    private Integer finishedCount;

    private Page<ThingReceiver> thingReceiversPage;

}
