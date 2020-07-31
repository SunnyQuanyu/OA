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
import java.util.List;
import java.util.Set;

/**
 * user
 *
 * @author
 */
@Data
public class User implements Serializable {
    /**
     * 自增Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 学号/工号
     */
    @NotNull(message = "用户学号不能为空")
    private String number;

    /**
     * 初试密码为学号
     */
    private String password;

    /**
     * 微信的open_id（公众号或者小程序）
     */
    private String wxOpenId;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 专业名称（比如通信工程专业）
     */
    private String majorName;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 身份 [1'学生', 0'老师'，系主任，等]
     */
    private Byte identity;

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

    @TableField(exist = false)
    private List<SysRole> roleList;

    @TableField(exist = false)
    private Set<SysPermission> permissionSet;

    private static final long serialVersionUID = 1L;
}