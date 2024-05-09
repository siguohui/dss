package com.xiaosi.wx.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * 单例的组件
 * 这里组合了@Component ， @Scope 所有spring 扫描时具有了这两个Annotation的能力：
 *
 * 单例，被扫描到并被容器实例化
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Scope("singleton")
@Component
@Inherited
public @interface SingletonComponent {

    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";
}
