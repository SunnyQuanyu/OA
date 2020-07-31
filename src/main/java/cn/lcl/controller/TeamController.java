package cn.lcl.controller;

import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.TeamAddDTO;
import cn.lcl.pojo.dto.TeamMembersDTO;
import cn.lcl.pojo.Team;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.TeamService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    TeamService service;

    @PostMapping("/add")
    public Result addTeam(@RequestBody @Valid TeamAddDTO team, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> service.saveTeam(team));
    }

    @PostMapping("/addMember")
    public Result addTeamMember(@RequestBody @Valid TeamMembersDTO teamMembers, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> service.saveTeamMembers(teamMembers));
    }

    @PostMapping("/delMember")
    public Result delTeamMember(@RequestBody @Valid TeamMembersDTO teamMembers, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> service.deleteTeamMembers(teamMembers));
    }

    @PostMapping
    public Result getTeam(@RequestBody @Valid SearchPageDTO<Team> pageDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> service.getTeam(pageDTO));
    }

    @PostMapping("/createdList")
    public Result createdList() {
        return service.listCreatedTeams();
    }

    @PostMapping("/joinedList")
    public Result joinedList() {
        return service.listJoinedTeams();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Team team) {
        return service.updateTeam(team);
    }

    @PostMapping("/del")
    public Result del(@RequestBody Team team) {
        return service.deleteTeam(team);
    }
}
