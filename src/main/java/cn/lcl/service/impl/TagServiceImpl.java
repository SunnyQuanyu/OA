package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.TagMapper;
import cn.lcl.pojo.Tag;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.pojo.vo.TagTreeNodeVO;
import cn.lcl.service.TagService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {


    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result saveTag(Tag tag) {
        Integer parentId = tag.getParentId();
        if (parentId != null) {
            Tag fatherTag = tagMapper.selectById(parentId);
            if (fatherTag != null) {
                if (fatherTag.getParentsIdPath().equals("")) {
                    tag.setParentsIdPath(String.valueOf(parentId));
                } else {
                    tag.setParentsIdPath(fatherTag.getParentsIdPath() + "," + parentId);

                }
                tag.setLevel(fatherTag.getLevel() + 1);
            }
        } else {
            tag.setLevel(0);
            tag.setParentsIdPath("");
        }
        // 如果没有父级相当于是一个0级的,给一个空字符串即可
        User user = AuthcUtil.getUser();
        tag.setManagerId(user.getId());
        tagMapper.insert(tag);
        return ResultUtil.success(tag);
    }

    @Override
    public Result getTag(Tag tag) {
        return null;
    }

    /**
     * getPublicState 魔法值：1 共有标签
     */
    @Override
    public Result listAvailableTags() {
        User user = AuthcUtil.getUser();
        List<Tag> tagList = new LambdaQueryChainWrapper<>(tagMapper).eq(Tag::getManagerId, user.getId())
                .or().eq(Tag::getPublicState, 1).list();

        List<TagTreeNodeVO> roots = new ArrayList<>();
        Iterator<Tag> iterator = tagList.iterator();
        while (iterator.hasNext()) {
            Tag tempTag = iterator.next();
            if (tempTag.getLevel() == 0) {
                TagTreeNodeVO tagTreeNode = getNodeByTag(tempTag);
                roots.add(tagTreeNode);
                iterator.remove();
            }
        }
        for (TagTreeNodeVO root : roots) {
            addNodeToTree(root, tagList);
        }

        return ResultUtil.success(roots);
    }

    @Override
    public Result listCreatedTags() {
        return ResultUtil.success(
                new LambdaQueryChainWrapper<>(tagMapper)
                        .eq(Tag::getManagerId, AuthcUtil.getUser().getId())
                        .list());
    }

    @Override
    public Result updateTag(Tag tag) {
        if (tag.getId() == null) {
            throw new MyException(ResultEnum.MISS_FIELD);
        }
        Tag tempTag = new Tag();
        tempTag.setId(tag.getId());
        tempTag.setTagName(tag.getTagName());
        tagMapper.updateById(tempTag);
        return ResultUtil.success(tempTag);
    }

    private boolean hasChild(Integer tid, List<Tag> tags) {
        for (Tag tag : tags) {
            if (tag.getParentId().equals(tid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 按照树的父子规则，将的list添加到node中
     *
     * @param node 树的一个根，从这个根开始找所有子节点
     * @param list 从数据库查找的tag list
     */
    private void addNodeToTree(TagTreeNodeVO node, List<Tag> list) {
        Iterator<Tag> iterator = list.iterator();
        while (iterator.hasNext()) {
            Tag tempTag = iterator.next();
            if (tempTag.getParentId().equals(node.getKey())) {
                TagTreeNodeVO tagTreeNode = getNodeByTag(tempTag);
                node.addChild(tagTreeNode);
                iterator.remove();
            }
        }
        if (list.size() == 0) {
            return;
        }
        for (TagTreeNodeVO child : node.getChildren()) {
            // 如果儿子还有儿子，进入递归。
            if (hasChild(child.getKey(), list)) {
                addNodeToTree(child, list);
            }
        }
    }

    private TagTreeNodeVO getNodeByTag(Tag tag) {
        TagTreeNodeVO tagTreeNode = new TagTreeNodeVO();
        tagTreeNode.setTitle(tag.getTagName());
        tagTreeNode.setKey(tag.getId());
        tagTreeNode.setValue(tag.getId());
        tagTreeNode.setPublicState(Integer.valueOf(tag.getPublicState()));
        return tagTreeNode;
    }
}
