package cn.lcl.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ThingFinishDTO {

    @NotNull(message = "thingId not null")
    private Integer thingId;

    private String content;

    // 用户上传的文件
    private List<MultipartFile> files;

    private String answersJSON;

}
