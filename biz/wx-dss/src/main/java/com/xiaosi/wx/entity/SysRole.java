package com.xiaosi.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaosi.wx.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role",autoResultMap = true)
@Schema(description = "角色")
@Accessors(chain = true)
public class SysRole extends BaseEntity<SysRole> {
    /** 主键ID **/
    @Schema(description = "主键ID")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    /** 角色名称 **/
    @Schema(description = "角色名称")
    @TableField(value = "name")
    private String name;
    /** 角色英文名称 **/
    @Schema(description = "角色英文名称")
    @TableField(value = "enname")
    private String enname;
    /** 角色编码 **/
    @Schema(description = "角色编码")
    @TableField(value = "code")
    private String code;
    /** 排序 **/
    @Schema(description = "排序")
    @TableField(value = "sort")
    private int sort;
    /** 数据范围 **/
    @Schema(description = "数据范围")
    @TableField(value = "data_scope")
    private String dataScope;
    /** 角色类型 **/
    @Schema(description = "角色类型")
    @TableField(value = "role_type")
    private String roleType;
    /** 是否可用(0-不可用 1-可用) **/
    @Schema(description = "是否可用(0-不可用 1-可用)")
    @TableField(value = "is_enable")
    private Boolean enable;
}
