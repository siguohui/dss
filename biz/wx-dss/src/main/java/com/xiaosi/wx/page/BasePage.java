package com.xiaosi.wx.page;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.xiaosi.wx.page.annotation.PageX;
import lombok.Data;

import java.io.Serializable;

@Data
@PageX
public abstract class BasePage extends Model {

    @TableField(exist = false)
    private int no = 1;
    @TableField(exist = false)
    private int size = 10;
    @TableField(exist = false)
    private Integer total;
    @TableField(exist = false)
    private boolean page = true;
}
