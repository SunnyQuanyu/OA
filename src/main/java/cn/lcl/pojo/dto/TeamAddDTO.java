package cn.lcl.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TeamAddDTO {
    @NotNull(message = "小组名称不能为空")
    private String teamName;

    @NotNull(message = "是否为共有标志不能为空")
    private Byte publicState;

    List<Integer> memberIdList;

}
