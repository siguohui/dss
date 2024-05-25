package com.xiaosi.wx.enums;

import com.xiaosi.wx.pojo.IResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存内容
 */
@Getter
@AllArgsConstructor
public enum CacheContentEnum implements IResult {

    CUSTOMER_CACHE(1, "客户缓存"),

    ORDER_CACHE(2, "订单缓存"),

    PRODUCT_CACHE(3, "产品缓存");

    private Integer code;

    private String msg;
}
