package com.xiaosi.wx.easyexcel.demo;

import java.lang.annotation.*;


/**
 * @author kuangql
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExplicitConstraint {


    /**
     * 定义固定下拉内容
     * @return a
     */
    String[] source() default {};

    /**
     * 列标号必须和字段下标一致
     * @return 0
     */
    int indexNum() default 0;

}
