package cn.lcl.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleAddUsersDTO {
    private String[] roleId;


    List<Integer> userIdList;

}
