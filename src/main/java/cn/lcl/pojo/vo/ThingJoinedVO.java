package cn.lcl.pojo.vo;

import cn.lcl.pojo.Thing;
import cn.lcl.pojo.ThingQuestion;
import cn.lcl.pojo.ThingSendFile;
import lombok.Data;

import java.util.List;

@Data
public class ThingJoinedVO {

    private Thing thing;

    private List<ThingSendFile> files;

    private List<ThingQuestion> questions;

}
