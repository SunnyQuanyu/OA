package cn.lcl.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagTreeNodeVO {

    private String title;

    private Integer key;

    private Integer value;

    private Integer publicState;

    private List<TagTreeNodeVO> children;

    public void addChild(TagTreeNodeVO tagTreeNode) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(tagTreeNode);
    }
}
