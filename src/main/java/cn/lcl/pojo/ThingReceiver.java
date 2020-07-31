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
 * thing_receiver
 *
 * @author
 */
@Data
public class ThingReceiver implements Serializable {
    /**
     * 自增Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 事务id
     */
    @NotNull(message = "thingId not null")
    private Integer thingId;

    /**
     * 接收者在user表对应的Id
     */
    @NotNull(message = "userId not null")
    private Integer userId;

    /**
     * 是否阅读了该事务（1已阅读；0未阅读）
     */
    private String hasRead;

    /**
     * 回执文字
     */
    private String content;

    /**
     * 是否点完成（1已完成；0未完成）
     */
    private String hasFinished;

    /**
     * 是否已经发送通知（1已发送；0未发送）
     */
    private String hasSendNote;

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
    private Thing thing;

    @TableField(exist = false)
    private String realName;

    private static final long serialVersionUID = 1L;
}