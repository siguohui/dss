package com.xiaosi.wx.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class BaseEntity<T> extends Model {

    private static final String CREATE_BY_FIELD = "create_by";
    private static final String CREATE_TIME_FIELD = "create_time";
    private static final String UPDATE_BY_FIELD = "update_by";
    private static final String UPDATE_TIME_FIELD = "update_time";

    /** 创建者 **/
    @Schema(description = "创建者")
    @TableField(value = CREATE_BY_FIELD, fill = FieldFill.INSERT)
    private String createBy;

    /** 创建时间 **/
    @Schema(description = "创建时间")
    @TableField(value = CREATE_TIME_FIELD, fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 更新者 **/
    @Schema(description = "更新者")
    @TableField(value = UPDATE_BY_FIELD,fill = FieldFill.INSERT_UPDATE, select = false)
    private String updateBy;

    /** 创建时间 **/
    @Schema(description = "更新时间")
    @TableField(value = UPDATE_TIME_FIELD, fill = FieldFill.INSERT_UPDATE, select = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat (pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /** 删除状态：0、未删除 1、已删除 **/
    @Schema(description = "删除状态：0、未删除 1、已删除")
    @TableLogic
    @TableField("deleted")
    private int deleted;

}
