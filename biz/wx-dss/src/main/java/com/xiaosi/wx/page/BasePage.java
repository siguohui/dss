package com.xiaosi.wx.page;

import com.xiaosi.wx.page.annotation.PageX;
import lombok.Data;

import java.io.Serializable;

@Data
@PageX
public abstract class BasePage implements Serializable {

    private int no;
    private int size;
    private Integer total;
    private boolean page = true;
}
