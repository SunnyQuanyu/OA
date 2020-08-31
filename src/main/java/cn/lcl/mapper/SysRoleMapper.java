package cn.lcl.mapper;

import cn.lcl.pojo.SysPermission;
import cn.lcl.pojo.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    SysRole selectById(Integer id);

    List<SysRole> selectList();

    List<SysPermission> getPermissionList(@Param("data") SysPermission searchVo,
                                          @Param("memberId") Integer memberId);

    Integer deletePermission(@Param("permissionId") Integer permissionId);

    Integer deleteRole(@Param("roleId") Integer roleId);
}
