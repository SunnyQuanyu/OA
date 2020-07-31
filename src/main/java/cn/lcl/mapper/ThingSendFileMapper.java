package cn.lcl.mapper;

import cn.lcl.pojo.ThingSendFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ThingSendFileMapper extends BaseMapper<ThingSendFile> {
}
