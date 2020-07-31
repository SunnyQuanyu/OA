package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.SysPermissionMapper;
import cn.lcl.mapper.SysRoleMapper;
import cn.lcl.mapper.SysRolePermissionMapper;
import cn.lcl.pojo.SysPermission;
import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysRolePermission;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.SysRoleService;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;

    @Transactional
    @Override
    public Result savePermissionOnRole(SysRolePermission sysRolePermission) {
        SysPermission permission = sysPermissionMapper.selectById(sysRolePermission.getPermissionId());
        SysRole role = sysRoleMapper.selectById(sysRolePermission.getRoleId());
        if (permission == null) {
            throw new MyException(ResultEnum.PERMISSION_NOT_FOUND);
        } else if (role == null) {
            throw new MyException(ResultEnum.ROLE_NOT_FOUND);
        }
        SysRolePermission one = new LambdaQueryChainWrapper<>(sysRolePermissionMapper)
                .eq(SysRolePermission::getRoleId, sysRolePermission.getRoleId())
                .eq(SysRolePermission::getPermissionId, sysRolePermission.getPermissionId())
                .one();
        if (one != null) {
            throw new MyException(ResultEnum.ROLE_ALREADLY_HAS_THIS_PERMISSION);
        }

        sysRolePermissionMapper.insert(sysRolePermission);

        return ResultUtil.success(sysRolePermission);
    }

    @Override
    public Result removePermissionOnRole(SysRolePermission sysRolePermission) {
        LambdaQueryWrapper<SysRolePermission> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysRolePermission::getPermissionId, sysRolePermission.getPermissionId())
                .eq(SysRolePermission::getRoleId, sysRolePermission.getRoleId());
        int delete = sysRolePermissionMapper.delete(queryWrapper);
        if (delete != 1) {
            throw new MyException(ResultEnum.DELETE_ROLE_PERMISSION_FAILED);
        }
        return ResultUtil.success(delete);
    }

    @Override
    public Result saveRole(SysRole role) {
        int insert = sysRoleMapper.insert(role);
        return ResultUtil.success(role);
    }

    @Override
    public Result listRoles() {
        return ResultUtil.success(new LambdaQueryChainWrapper<>(sysRoleMapper).list());
    }

    @Override
    public Result getRole(SysRole sysRole) {
        return ResultUtil.success(sysRoleMapper.selectById(sysRole.getId()));
    }
}
