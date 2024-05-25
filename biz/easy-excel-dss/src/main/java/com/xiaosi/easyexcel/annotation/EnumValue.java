package com.xiaosi.easyexcel.annotation;

import com.xiaosi.easyexcel.handler.EnumValueValidated;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Constraint(validatedBy = {EnumValueValidated.class})
public @interface EnumValue {
    /**
     * 字符串数组
     */
    String[] strValues() default {};

    String message() default "所传参数不在允许的值范围内";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
