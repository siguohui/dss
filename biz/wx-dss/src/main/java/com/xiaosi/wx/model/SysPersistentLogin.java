package com.xiaosi.wx.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaosi.wx.base.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author sgh
 * @since 2024-05-06 04:20:37
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_persistent_login")
@Schema(name = "SysPersistentLogin", description = "")
public class SysPersistentLogin{

    private static final long serialVersionUID = 1L;

    @TableField("series")
    private String series;

    @TableField("username")
    private String username;

    @TableField("token")
    private String token;

    @TableField("lastUsed")
    private Date lastUsed;
}
