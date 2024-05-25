package com.xiaosi.wx.easyexcel.annotation;

import com.xiaosi.wx.easyexcel.utils.EnumUtils;
import com.xiaosi.wx.pojo.IResult;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckValid.Validator.class)
public @interface CheckValid {

    String message() default "{custom.value.invalid}";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends IResult> enumClass();

    class Validator implements ConstraintValidator<CheckValid, Object> {

        private Class<? extends IResult> enumClass;
        boolean nullable = false;

        @Override
        public void initialize(CheckValid enumValue) {
            enumClass = enumValue.enumClass();
            nullable = enumValue.nullable();
            ConstraintValidator.super.initialize(enumValue);
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null && nullable) {
                return Boolean.TRUE;
            }

            if(value == null && !nullable){
                return Boolean.FALSE;
            }

            if (enumClass == null) {
                return Boolean.TRUE;
            }

            String enumCode = value.toString();
            return EnumUtils.isValidCode(enumClass, enumCode);
        }
    }
}
