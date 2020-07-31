package cn.lcl.service;

import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysRolePermission;
import cn.lcl.pojo.result.Result;

public interface SysRoleService {

    // 对某个角色添加某个权限
    Result savePermissionOnRole(SysRolePermission sysRolePermission);

    // 对某个角色删除某个权限
    Result removePermissionOnRole(SysRolePermission sysRolePermission);

    // 添加一个角色
    Result saveRole(SysRole role);

    // 查询角色列表
    Result listRoles();

    // 获得一个角色
    Result getRole(SysRole sysRole);
}
