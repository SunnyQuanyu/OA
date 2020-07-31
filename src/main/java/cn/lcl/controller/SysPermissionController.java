package cn.lcl.controller;

import cn.lcl.pojo.result.Result;
import cn.lcl.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysPermissionController {

    @Autowired
    SysPermissionService sysPermissionService;

    @PostMapping("/permissions")
    public Result getPermissions() {
        return sysPermissionService.listPermissions();
    }
}
