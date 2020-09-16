package cn.lcl.mapper;

import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    List<SysRole> getUserRoleList(Integer uid);

    List<SysUserRole> getUsersIdByRoleId(String roleId);

    List<Integer> selectUsersIdByRoleId(String roleId);

}
