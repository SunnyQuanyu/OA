package cn.lcl.mapper;

import cn.lcl.pojo.TeamMember;
import cn.lcl.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeamMemberMapper extends BaseMapper<TeamMember> {
    List<User> selectUsersByTeamId(String teamId);
}
