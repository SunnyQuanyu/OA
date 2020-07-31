package cn.lcl.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * thing_question
 *
 * @author
 */
@Data
public class ThingQuestion implements Serializable {
    /**
     * 自增Id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 事务表id
     */
    private Integer thingId;

    /**
     * 问题内容
     */
    private String title;

    /**
     * 题目标号
     */
    private Integer number;

    /**
     * 最多几选（最多选择几个）
     */
    private String maxChoose;

    /**
     * 回执类型（1文字填空、2投票类型、3选项类型、4打分类型）
     */
    private String replyType;

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
    private List<QuestionOption> options;

    @TableField(exist = false)
    private List<QuestionAnswer> answers;


    private static final long serialVersionUID = 1L;
}