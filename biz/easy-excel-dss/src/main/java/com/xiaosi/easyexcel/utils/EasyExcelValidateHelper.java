package com.xiaosi.easyexcel.utils;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.groups.Default;
import lombok.Getter;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

import java.lang.reflect.Field;
import java.util.Set;

public class EasyExcelValidateHelper {

    private static final Validator validator;

    static {
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    /*@Getter
    static Validator validator;*/

    public static <T> String validate(T t) {
        StringBuilder result = new StringBuilder();


        /*HibernateValidatorConfiguration configure = Validation.byProvider(HibernateValidator.class).configure();
        ValidatorFactory validatorFactory = configure.failFast(false).buildValidatorFactory();
        // 根据validatorFactory拿到一个Validator
        Validator validator1 = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validate2 = validator1.validate(t);
        System.out.println(validate2);*/


        Set<ConstraintViolation<T>> set = validator.validate(t, Default.class);
        if (set != null && !set.isEmpty()) {
            for (ConstraintViolation<T> cv : set) {
                Field declaredField = ReflectUtil.getField(t.getClass(), cv.getPropertyPath().toString());
                ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
                result.append(annotation.value()[0]).append(cv.getMessage()).append(";");
            }
        }
        return result.toString();
    }


}
