package cn.lcl.pojo.dto;

import cn.lcl.pojo.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TeamMembersDTO {

    @NotNull(message = "小组id不能为空")
    Integer teamId;

    List<User> members;

}
