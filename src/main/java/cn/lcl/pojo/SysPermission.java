package cn.lcl.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * sys_permission
 *
 * @author
 */
@Data
public class SysPermission implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名称（即左侧菜单栏的栏目名称，比如：用户管理、小组管理、事务管理、标签管理、用户-角色-权限管理）
     */
    private String permissionName;

    /**
     * 对应的前端路由
     */
    private String frontRoute;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private LocalDateTime createTime;

    /**
     * 创建者在user表的id
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private Integer creatorId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonIgnore
    private LocalDateTime updateTime;

    /**
     * 更新者的id
     */
    @TableField(fill = FieldFill.UPDATE)
    @JsonIgnore
    private Integer updatorId;

    /**
     * 删除标志（0表示未删除，id表示已删除）
     */
    @TableField(select = false, fill = FieldFill.INSERT)
    @JsonIgnore
    private Integer deleteFlg;

    private static final long serialVersionUID = 1L;
}