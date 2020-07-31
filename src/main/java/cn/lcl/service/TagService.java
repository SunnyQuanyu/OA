package cn.lcl.service;

import cn.lcl.pojo.Tag;
import cn.lcl.pojo.result.Result;

public interface TagService {
    // 添加一个标签
    Result saveTag(Tag tag);

    // 查找一个标签
    Result getTag(Tag tag);

    // 获取所有个人私有和共有的标签
    Result listAvailableTags();

    // 获取所有创建的饿标签
    Result listCreatedTags();

    // 更新一个标签信息
    Result updateTag(Tag tag);

}
