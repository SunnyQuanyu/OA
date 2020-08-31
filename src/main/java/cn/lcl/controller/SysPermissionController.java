package cn.lcl.controller;

import cn.lcl.pojo.SysPermission;
import cn.lcl.pojo.Team;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.SysPermissionService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SysPermissionController {

    @Autowired
    SysPermissionService sysPermissionService;

    @PostMapping("/permissions")
    public Result getPermissions() {
        return sysPermissionService.listPermissions();
    }

    @PostMapping("/permissionList")
    public Result permissionList(@RequestBody @Valid SearchPageDTO<SysPermission> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> sysPermissionService.permissionList(page));
    }

    @PostMapping("/deletePermission")
    public Result deleteThing(@RequestBody @Valid IdDTO permissionId, BindingResult result){
        return ResultUtil.vaildFieldError(result, () -> sysPermissionService.deletePermission(permissionId));
    }

    @PostMapping("/update")
    public Result update(@RequestBody SysPermission sysPermission) {
        return sysPermissionService.updateSysPermission(sysPermission);
    }

    @PostMapping("/addPermission")
    public Result add(@RequestBody @Valid SysPermission permission, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> sysPermissionService.savePermission(permission));
    }
}
