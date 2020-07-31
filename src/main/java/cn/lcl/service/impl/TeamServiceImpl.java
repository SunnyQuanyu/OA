package cn.lcl.service.impl;

import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.TeamAddDTO;
import cn.lcl.pojo.dto.TeamMembersDTO;
import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.TeamMapper;
import cn.lcl.mapper.TeamMemberMapper;
import cn.lcl.mapper.UserMapper;
import cn.lcl.pojo.Team;
import cn.lcl.pojo.TeamMember;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.TeamService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamMapper teamMapper;
    @Autowired
    TeamMemberMapper teamMemberMapper;
    @Autowired
    UserMapper userMapper;

    @Transactional
    @Override
    public Result saveTeam(TeamAddDTO team) {
        User user = AuthcUtil.getUser();
        Team resultTeam = new Team();
        resultTeam.setManagerId(user.getId());
        resultTeam.setTeamName(team.getTeamName());
        resultTeam.setPublicState(team.getPublicState());
        teamMapper.insert(resultTeam);
        if (team.getMemberIdList() != null) {
            HashSet<Integer> ids = new HashSet<>(team.getMemberIdList());
            for (Integer id : ids) {
                TeamMember teamMember = new TeamMember();
                teamMember.setTeamId(resultTeam.getId());
                teamMember.setUserId(id);
                int insert = teamMemberMapper.insert(teamMember);
                if (insert != 1) {
                    throw new MyException(ResultEnum.TEAM_MEMBER_INSERT_ERROR);
                }
            }
        }
        return ResultUtil.success(resultTeam);
    }

    @Transactional
    @Override
    public Result saveTeamMembers(TeamMembersDTO teamMembers) {
        Integer teamId = teamMembers.getTeamId();
        validTeamExist(teamId);
        for (User member : teamMembers.getMembers()) {
            List<TeamMember> list = getTeamMemberList(teamId, member);
            if (list.size() != 0) {
                continue;
            }
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(teamId);
            teamMember.setUserId(member.getId());
            int insert = teamMemberMapper.insert(teamMember);
            if (insert != 1) {
                throw new MyException(ResultEnum.TEAM_MEMBER_INSERT_ERROR);
            }
        }
        return ResultUtil.success(teamMembers.getMembers().size());
    }

    @Transactional
    @Override
    public Result deleteTeamMembers(TeamMembersDTO teamMembers) {
        Integer teamId = teamMembers.getTeamId();
        validTeamExist(teamId);
        for (User member : teamMembers.getMembers()) {
            List<TeamMember> list = getTeamMemberList(teamId, member);
            if (list.size() == 0) {
                throw new MyException(ResultEnum.TEAM_MEMBER_NOT_FOUND);
            }
            int insert = teamMemberMapper.deleteById(list.get(0).getId());
            if (insert != 1) {
                throw new MyException(ResultEnum.TEAM_MEMBER_DELETE_ERROR);
            }
        }
        return ResultUtil.success(teamMembers.getMembers().size());
    }

    @Override
    public Result updateTeam(Team team) {
        if (team.getId() == null) {
            throw new MyException(ResultEnum.MISS_FIELD);
        }
        int i = teamMapper.updateById(team);
        if (i == 0) {
            throw new MyException(ResultEnum.TEAM_UPDATE_FAILED);
        }
        return ResultUtil.success(team);
    }

    @Override
    public Result deleteTeam(Team team) {
        if (team.getId() == null) {
            throw new MyException(ResultEnum.MISS_FIELD);
        }
        Integer integer = teamMapper.deleteTeamAndItsMemberRelation(team.getId());
        return ResultUtil.success(integer);
    }

    @Override
    public Result getTeam(SearchPageDTO<Team> searchPageDTO) {
        Team team = searchPageDTO.getData();
        Team resultTeam = teamMapper.selectById(team.getId());
        if (resultTeam == null) {
            throw new MyException(ResultEnum.TEAM_NOT_FOUND);
        }
        Page<User> userPage = teamMapper.selectMembersInTeam(
                new Page<>(searchPageDTO.getPageCurrent(), searchPageDTO.getPageSize()),
                resultTeam.getId());
        resultTeam.setMembersPage(userPage);
        return ResultUtil.success(resultTeam);
    }

    @Override
    public Result listCreatedTeams() {
        User user = AuthcUtil.getUser();
        List<Team> list = new LambdaQueryChainWrapper<>(teamMapper)
                .eq(Team::getManagerId, user.getId()).list();
        return ResultUtil.success(list);
    }

    @Override
    public Result listJoinedTeams() {
        User user = AuthcUtil.getUser();
        return ResultUtil.success(teamMapper.selectTeamListByMemberId(user.getId()));
    }

    // 一下为此类代码分离

    private List<TeamMember> getTeamMemberList(Integer teamId, User member) {
        User user = userMapper.selectById(member.getId());
        if (user == null) {
            throw new MyException(ResultEnum.USER_NOT_FOUND);
        }
        return new LambdaQueryChainWrapper<>(teamMemberMapper)
                .eq(TeamMember::getTeamId, teamId)
                .eq(TeamMember::getUserId, user.getId()).list();
    }

    private void validTeamExist(Integer teamId) {
        Team team = teamMapper.selectById(teamId);
        if (team == null) {
            throw new MyException(ResultEnum.TEAM_NOT_FOUND);
        }
    }
}
