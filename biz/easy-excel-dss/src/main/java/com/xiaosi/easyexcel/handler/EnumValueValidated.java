package com.xiaosi.easyexcel.handler;

import com.google.common.collect.Sets;
import com.xiaosi.easyexcel.annotation.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;
import java.util.Set;

public class EnumValueValidated implements ConstraintValidator<EnumValue, Object> {
    private Set<String> strValues;
    @Override
    public void initialize(EnumValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        strValues = Sets.newHashSet(constraintAnnotation.strValues());
        //将枚举类的name转小写存入strValues里面，作为校验参数
//        Optional.ofNullable(constraintAnnotation.enumClass()).ifPresent(e -> Arrays.stream(e).forEach(
//                c -> Arrays.stream(c.getEnumConstants()).forEach(v -> strValues.add(v.toString().toLowerCase()))
//        ));
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value instanceof String) {
            return strValues.contains(value);
        }

        return false;
    }
}
