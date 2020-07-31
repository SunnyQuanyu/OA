package cn.lcl.mapper;

import cn.lcl.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
