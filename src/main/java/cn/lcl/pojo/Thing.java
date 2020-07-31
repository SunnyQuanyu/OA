package cn.lcl.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * thing
 *
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thing implements Serializable {
    /**
     * 自增Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 事情/事务标题
     */
    @NotNull(message = "title not null")
    private String title;

    /**
     * 通知内容
     */
    @NotNull(message = "content not null")
    private String content;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 截止时间
     */
    private Date endTime;

    /**
     * 是否有发送附件（1有发送附件；0没有发送附件）
     */
    @NotNull(message = "hasSendFile not null")
    private String hasSendFile;

    /**
     * 是否需要回执投票（1需要回执，则关联了thing_question表；0不需要回执，则未关联thing_question表）
     */
    @NotNull(message = "needAnswer not null")
    private String needAnswer;

    /**
     * 是否需要回执文件（1需要回执；0不需要回执）
     */
    @NotNull(message = "needFileReply not null")
    private String needFileReply;

    /**
     * 是否需要点确认已回执（1需要回执；0不需要回执）
     */
    @NotNull(message = "needFinish not null")
    private String needFinish;

    /**
     * 如果有父级事务，该项就是父级事务的id（主要用于追加事务）
     */
    private Integer parentThingId;

    /**
     * 再次提醒时间（通知发出时视作第一次提醒）
     */
    private LocalDateTime remindTime;

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

    // 发送人的realName
    @TableField(exist = false)
    private String realName;

    @TableField(exist = false)
    private String tagName;

    private static final long serialVersionUID = 1L;
}