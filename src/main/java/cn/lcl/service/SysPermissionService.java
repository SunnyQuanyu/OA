package cn.lcl.service;

import cn.lcl.pojo.SysPermission;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.result.Result;

public interface SysPermissionService {
    // 获取所有权限
    Result listPermissions();

    //搜索所有权限
    Result permissionList(SearchPageDTO<SysPermission> page);

    //删除权限
    Result deletePermission(IdDTO permissionId);

    //更新一条权限
    Result updateSysPermission(SysPermission sysPermission);

    //新增一条权限
    Result savePermission(SysPermission permission);
}
