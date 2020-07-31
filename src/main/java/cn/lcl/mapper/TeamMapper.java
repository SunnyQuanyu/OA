package cn.lcl.mapper;

import cn.lcl.pojo.Team;
import cn.lcl.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeamMapper extends BaseMapper<Team> {
    Page<User> selectMembersInTeam(Page<?> page, Integer teamId);

    List<Team> selectTeamListByMemberId(Integer memberId);

    Integer deleteTeamAndItsMemberRelation(Integer teamId);

}
