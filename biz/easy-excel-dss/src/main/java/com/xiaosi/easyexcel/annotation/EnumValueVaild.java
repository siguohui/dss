package com.xiaosi.easyexcel.annotation;

import com.xiaosi.easyexcel.enums.BaseEnum;
import com.xiaosi.easyexcel.utils.EnumUtils;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueVaild.Validator.class)
public @interface EnumValueVaild {
    String message() default "{custom.value.invalid}";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends BaseEnum> enumClass();

    class Validator implements ConstraintValidator<EnumValueVaild, Object> {

        private Class<? extends BaseEnum> enumClass;
        boolean nullable = false;

        @Override
        public void initialize(EnumValueVaild enumValue) {
            enumClass = enumValue.enumClass();
            nullable = enumValue.nullable();
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

            String enumCode = (String)value;
            return EnumUtils.isValidCode(enumClass, enumCode);
        }
    }
}
