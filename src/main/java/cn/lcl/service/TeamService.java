package cn.lcl.service;

import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.TeamAddDTO;
import cn.lcl.pojo.dto.TeamMembersDTO;
import cn.lcl.pojo.Team;
import cn.lcl.pojo.result.Result;

public interface TeamService {


    // 保存一个小组
    Result saveTeam(TeamAddDTO team);

    // 保存一个小组的小组成员
    Result saveTeamMembers(TeamMembersDTO teamMembers);

    // 删除一个小组的小组成员
    Result deleteTeamMembers(TeamMembersDTO teamMembers);

    /**
     * 只更新基本信息
     *
     * @param team 要包含id属性
     * @return 修改后的result
     */
    Result updateTeam(Team team);

    // 删除一个小组
    Result deleteTeam(Team team);

    /**
     * @param searchPageDTO data中是team对象
     * @return team信息和对应的页的学生信息
     */
    Result getTeam(SearchPageDTO<Team> searchPageDTO);

    // 获取所有创建的小组
    Result listCreatedTeams();

    // 获取所有所在的小组
    Result listJoinedTeams();

}
