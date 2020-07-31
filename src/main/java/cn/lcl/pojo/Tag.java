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
 * tag
 *
 * @author
 */
@Data
public class Tag implements Serializable {
    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 公有标识1；私有标识0
     */
    @NotNull(message = "公私有标志缺失")
    private Byte publicState;

    /**
     * 标签名称
     */
    @NotNull(message = "标签名不能为空")
    private String tagName;

    /**
     * 标签管理者在user表的id
     */
    private Integer managerId;

    /**
     * 标签级别（一级1、二级2、三级3）
     */
    private Integer level;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 父级标签的id（从高到低）
     */
    private String parentsIdPath;

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