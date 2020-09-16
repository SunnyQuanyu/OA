package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.SysPermissionMapper;
import cn.lcl.mapper.SysRoleMapper;
import cn.lcl.mapper.SysRolePermissionMapper;
import cn.lcl.mapper.SysUserRoleMapper;
import cn.lcl.pojo.*;
import cn.lcl.pojo.dto.IdDTO;
import cn.lcl.pojo.dto.RoleAddUsersDTO;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.SysRoleService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

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

    @Override
    public Result deleteRole(IdDTO roleId) {

        return ResultUtil.success(sysRoleMapper.deleteRole(roleId.getId()));
    }


    @Override
    public Result saveRoleUsers(RoleAddUsersDTO roleUsers) {
        if(roleUsers.getRoleId() != null) {
            for (String roleId : roleUsers.getRoleId()) {
                List<SysUserRole> getUsersIdByRoleId= sysUserRoleMapper.getUsersIdByRoleId(roleId);
                List<Integer> users = new ArrayList<>();
                for(SysUserRole userRole : getUsersIdByRoleId){
                    users.add(userRole.getUserId());
                }
                if(roleUsers.getUserIdList().size() > 0) {
                    for (Integer userId : roleUsers.getUserIdList()) {

                        if (!users.contains(userId)) {
                            SysUserRole userRole = new SysUserRole();
                            userRole.setRoleId(roleId);
                            userRole.setUserId(userId);
                            int insert = sysUserRoleMapper.insert(userRole);
                            if (insert != 1) {
                                throw new MyException(ResultEnum.TEAM_MEMBER_INSERT_ERROR);
                            }
                        }
                    }
                }
            }
        }
        return ResultUtil.success(roleUsers.getUserIdList().size());
    }

    @Override
    public Result updateRole(SysRole role) {
        if (role.getId() == null) {
            throw new MyException(ResultEnum.MISS_FIELD);
        }
        int i = sysRoleMapper.updateById(role);
        if (i == 0) {
            throw new MyException(ResultEnum.TEAM_UPDATE_FAILED);
        }
        return ResultUtil.success(role);
    }
}
