package cn.lcl.mapper;

import cn.lcl.pojo.Tag;
import cn.lcl.pojo.Thing;
import cn.lcl.pojo.vo.ThingCreatedListOneVO;
import cn.lcl.pojo.vo.ThingCreatedSearchVo;
import cn.lcl.pojo.vo.ThingCreatedVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ThingMapper extends BaseMapper<Thing> {

    Page<ThingCreatedListOneVO> getCreatedThingsByUserId(@Param("page") Page<?> page, @Param("userId") Integer userId);

    ThingCreatedVO getCreatedThingAboutReceiverNum(Integer thingId);

    Thing getThingById(Integer thingId);

    Thing getThingById11(Integer thingId);

    Integer deleteThingById(Integer thingId);

    List<Tag> getTagByThingId(Integer thingId);

    Page<ThingCreatedListOneVO> getCreatedThingsExpectTag(@Param("page") Page<?> page, @Param("userId") Integer userId);

    List<ThingCreatedListOneVO> getCreatedThings(@Param("data") ThingCreatedSearchVo searchVo,
                                                 @Param("userId") Integer userId);
    List<ThingCreatedListOneVO> getJoinedThings(@Param("data") ThingCreatedSearchVo searchVo,
                                                 @Param("userId") Integer userId);
    List<ThingCreatedListOneVO> getTeamThingByTeamId(@Param("teamId") Integer teamId);
}
