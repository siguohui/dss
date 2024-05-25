package com.xiaosi.easyexcel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存内容
 */
@Getter
@AllArgsConstructor
public enum  CacheContentEnum implements BaseEnum {

    //
    CUSTOMER_CACHE("CUSTOMER_CACHE", "客户缓存"),

    ORDER_CACHE("ORDER_CACHE", "订单缓存"),

    PRODUCT_CACHE("PRODUCT_CACHE", "产品缓存");

    private String code;

    private String desc;


    @Override
    public String getEnumCode() {
        return code;
    }

    @Override
    public String getEnumDesc() {
        return desc;
    }
}
