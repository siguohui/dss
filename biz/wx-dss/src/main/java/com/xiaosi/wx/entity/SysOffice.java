package com.xiaosi.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaosi.wx.base.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 机构表
 * </p>
 *
 * @author sgh
 * @since 2024-05-10 05:15:16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_office")
@Schema(name = "SysOffice", description = "机构表")
public class SysOffice extends BaseEntity {

    @Schema(description = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "父级编号")
    @TableField("parent_id")
    private Long parentId;

    @Schema(description = "所有父级编号")
    @TableField("parent_ids")
    private String parentIds;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "机构编码")
    @TableField("code")
    private String code;

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "机构类型")
    @TableField("type")
    private String type;

    @Schema(description = "机构等级")
    @TableField("grade")
    private String grade;

    @Schema(description = "是否有子节点")
    @TableField("hasleaf")
    private Boolean hasleaf;

    @Schema(description = "负责人")
    @TableField("master")
    private String master;

    @Schema(description = "是否启用")
    @TableField("is_enable")
    private Boolean isEnable;

    @Schema(description = "备注信息")
    @TableField("remarks")
    private String remarks;

    @Schema(description = "删除标记")
    @TableField("del_flag")
    private String delFlag;

    @Schema(description = "历史单位编码")
    @TableField("history_no")
    private String historyNo;

    @Schema(description = "简称")
    @TableField("short_name")
    private String shortName;

    @Schema(description = "简码")
    @TableField("short_code")
    private String shortCode;
}
