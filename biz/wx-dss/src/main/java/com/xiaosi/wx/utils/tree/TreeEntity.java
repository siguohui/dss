package com.xiaosi.wx.utils.tree;

import java.util.List;

/**
 * @description: DepartmentResp
 * @author: 懒虫虫~
 */
public interface TreeEntity<E> {
    public String getId();

    public String getParentId();

    public void setChildList(List<E> childList);
}
