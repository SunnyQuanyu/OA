package cn.lcl.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * sys_role_permission
 *
 * @author
 */
@Data
public class SysRolePermission implements Serializable {
    /**
     * 自增Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色role表id
     */
    @NotNull(message = "角色不能为空")
    private String roleId;

    /**
     * 权限permission表Id
     */
    @NotNull(message = "权限不能为空")
    private Integer permissionId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建者在user表的id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer creatorId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新者的id
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer updatorId;

    /**
     * 删除标志（0表示未删除，id表示已删除）
     */
    @TableField(select = false, fill = FieldFill.INSERT)
    @JsonIgnore
    private Integer deleteFlg;

    private static final long serialVersionUID = 1L;
}