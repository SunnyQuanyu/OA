package cn.lcl.mapper;

import cn.lcl.pojo.SysPermission;
import cn.lcl.pojo.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<Integer> selectUsersIdByPermissionId(String permissionId);
}
