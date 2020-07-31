package cn.lcl.controller;

import cn.lcl.pojo.Tag;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.TagService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping
    public Result add(@RequestBody @Valid Tag tag, BindingResult result) {
        return ResultUtil.vaildFieldError(result,()->tagService.saveTag(tag));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Tag tag) {
        return tagService.updateTag(tag);
    }

    @PostMapping("/get")
    public Result get(@RequestBody Tag tag) {
        return tagService.getTag(tag);
    }

    @PostMapping("/tags")
    public Result getTags() {
        return tagService.listAvailableTags();
    }

    @PostMapping("/createdTags")
    public Result createdTags() {
        return tagService.listCreatedTags();
    }
}
