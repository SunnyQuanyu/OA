package cn.lcl.service.impl;

import cn.lcl.mapper.SysPermissionMapper;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.SysPermissionService;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    SysPermissionMapper sysPermissionMapper;


    @Override
    public Result listPermissions() {
        return ResultUtil.success(new LambdaQueryChainWrapper<>(sysPermissionMapper).list());
    }
}
