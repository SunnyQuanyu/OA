package cn.lcl.controller;

import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysRolePermission;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.SysRoleService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @PostMapping("/add")
    public Result add(@RequestBody @Valid SysRole role, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> sysRoleService.saveRole(role));
    }

    @PostMapping("/addPermission")
    public Result addPermission(@RequestBody @Valid SysRolePermission rolePermission,
                                BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> sysRoleService.savePermissionOnRole(rolePermission));
    }

    @PostMapping("/delPermission")
    public Result delPermission(@RequestBody @Valid SysRolePermission rolePermission,
                                BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> sysRoleService.removePermissionOnRole(rolePermission));
    }

    @PostMapping("/getRoles")
    public Result getRoles() {
        return sysRoleService.listRoles();
    }

    @PostMapping("/getRole")
    public Result getRole(@RequestBody SysRole sysRole) {
        return sysRoleService.getRole(sysRole);
    }

}
