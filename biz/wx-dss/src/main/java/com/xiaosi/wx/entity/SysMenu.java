package com.xiaosi.wx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaosi.wx.annotation.EncryptedTable;
import com.xiaosi.wx.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author sgh
 * @since 2024-05-05 10:08:00
 */
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu",autoResultMap = true)
@Schema(name = "SysMenu", description = "")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("parent_id")
    private Long parentId;

    @TableField("name")
    private String name;

    @TableField("path")
    private String path;

    @TableField("sort")
    private Integer sort;

    @TableField("perms")
    private String perms;

    @TableField("type")
    private Integer type;

    @TableField("icon")
    private String icon;
}
