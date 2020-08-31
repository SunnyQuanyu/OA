package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.SysPermissionMapper;
import cn.lcl.mapper.SysRoleMapper;
import cn.lcl.pojo.SysPermission;
import cn.lcl.pojo.Team;
import cn.lcl.pojo.User;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.SysPermissionService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    SysPermissionMapper sysPermissionMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;


    @Override
    public Result listPermissions() {
        return ResultUtil.success(new LambdaQueryChainWrapper<>(sysPermissionMapper).list());
    }

    @Override
    public Result permissionList(SearchPageDTO<SysPermission> page) {
        User user = AuthcUtil.getUser();
        SysPermission data = page.getData();
        List<SysPermission> team1 = sysRoleMapper.getPermissionList(data, user.getId());
        return ResultUtil.success(team1);
    }

    @Override
    public Result deletePermission(IdDTO permissionId) {

        return ResultUtil.success(sysRoleMapper.deletePermission(permissionId.getId()));
    }

    @Override
    public Result updateSysPermission(SysPermission sysPermission) {
        if (sysPermission.getId() == null) {
            throw new MyException(ResultEnum.MISS_FIELD);
        }
        int i = sysPermissionMapper.updateById(sysPermission);
        if (i == 0) {
            throw new MyException(ResultEnum.TEAM_UPDATE_FAILED);
        }
        return ResultUtil.success(sysPermission);
    }

    @Override
    public Result savePermission(SysPermission permission) {
        int insert = sysPermissionMapper.insert(permission);
        return ResultUtil.success(permission);
    }
}
