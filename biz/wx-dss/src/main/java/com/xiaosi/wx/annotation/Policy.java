package com.xiaosi.wx.annotation;

import com.xiaosi.wx.enums.PolicyEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Policy {
    PolicyEnum value();
}
