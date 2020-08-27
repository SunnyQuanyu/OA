package cn.lcl.mapper;

import cn.lcl.pojo.ThingTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



@Repository
@Mapper
public interface ThingTeamMapper extends BaseMapper<ThingTeam> {
}
