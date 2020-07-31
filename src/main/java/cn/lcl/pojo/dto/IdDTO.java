package cn.lcl.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdDTO {
    @NotNull(message = "id不能为空")
    Integer id;
}
