package cn.lcl.service;

import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysRolePermission;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.RoleAddUsersDTO;
import cn.lcl.pojo.result.Result;
import org.apache.ibatis.annotations.Param;

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

    //删除一个角色
    Result deleteRole(IdDTO roleId);

    // 保存批量设置角色的角色和用户关系
    Result saveRoleUsers(RoleAddUsersDTO roleUsers);

    /**
     * 只更新基本信息
     *
     * @param role 要包含id属性
     * @return 修改后的result
     */
    Result updateRole(SysRole role);
}
