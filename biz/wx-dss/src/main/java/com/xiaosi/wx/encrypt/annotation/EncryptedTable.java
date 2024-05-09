package com.xiaosi.wx.encrypt.annotation;

import org.checkerframework.checker.units.qual.C;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Scope("singleton")
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EncryptedTable {

    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";

    @AliasFor("name")
    String[] path() default {};

    @AliasFor("path")
    String[] name() default {};
}
