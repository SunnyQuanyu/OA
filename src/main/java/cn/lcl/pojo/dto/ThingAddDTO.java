package cn.lcl.pojo.dto;

import cn.lcl.pojo.Thing;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加一个事务时传输的实体，包括了事务所有关联的东西。
 */
@EqualsAndHashCode(callSuper = true) // callSuper是在验证equals时是否比较父类的field
@Data
public class ThingAddDTO extends Thing {

    // 是否通过小组录入人员，如果不使用小组，则使用receiverIds
    @NotNull(message = "userTeam not null")
    private Boolean userTeam;

    private String[] teamId;

    private Integer[] receiverIds;


    @NotNull(message = "tagId not null")
    private String[] tagId;

    private List<MultipartFile> files;

    private String questionsJSON;

}