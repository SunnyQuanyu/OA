package cn.lcl.mapper;

import cn.lcl.pojo.Team;
import cn.lcl.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeamMapper extends BaseMapper<Team> {
    Page<User> selectMembersInTeam(@Param("page") Page<?> page,@Param("teamId") Integer teamId);

    List<Team> selectTeamListByMemberId(@Param("memberId") Integer memberId);

    Integer deleteTeamAndItsMemberRelation(@Param("teamId") Integer teamId);

    Team selectTeamCreatorName(@Param("creatorId") Integer creatorId);

    List<Team> getCreatedTeams(@Param("data") Team searchVo,
                                                 @Param("userId") Integer userId);

    List<Team> getJoinedTeams(@Param("data") Team searchVo,
                               @Param("memberId") Integer memberId);
}
