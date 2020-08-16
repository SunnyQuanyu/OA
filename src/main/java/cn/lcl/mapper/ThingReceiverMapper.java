package cn.lcl.mapper;

import cn.lcl.pojo.Tag;
import cn.lcl.pojo.ThingReceiver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface ThingReceiverMapper extends BaseMapper<ThingReceiver> {
    Page<ThingReceiver> selectThingReceiversByReceiverId(@Param("page") Page<?> page, @Param("userId") Integer userId, @Param("thingReceiver") ThingReceiver thingReceiver);

    Page<ThingReceiver> selectThingReceiversAndUserRealNamePageByThingId(@Param("page") Page<?> page, @Param("thingReceiver") ThingReceiver thingReceiver);

    ThingReceiver selectThingReceiverAndUserRealNamePage(@Param("thingId") Integer thingId,@Param("userId") Integer userId);

    Page<ThingReceiver> selectThingReceiversByReceiverIdExceptTag(@Param("page") Page<?> page, @Param("userId") Integer userId, @Param("thingReceiver") ThingReceiver thingReceiver);

    List<Tag> getTagByReceiverId(@Param("userId") Integer userId,@Param("thingId") Integer thingId);

    Integer deleteThingByUserId(Integer thingId,Integer userId);
}
