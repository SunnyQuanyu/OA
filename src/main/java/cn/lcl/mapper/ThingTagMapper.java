package cn.lcl.mapper;

import cn.lcl.pojo.ThingTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ThingTagMapper extends BaseMapper<ThingTag> {
}
