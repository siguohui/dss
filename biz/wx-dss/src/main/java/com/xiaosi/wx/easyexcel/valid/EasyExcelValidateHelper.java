package com.xiaosi.wx.easyexcel.valid;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.groups.Default;
import org.hibernate.validator.HibernateValidator;

import java.lang.reflect.Field;
import java.util.Set;

public class EasyExcelValidateHelper {

    /*@Getter
    static Validator validator;*/

    private static final Validator validator;

    static {
        /*ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();*/
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public static <T> String validate(T t) {
        StringBuilder result = new StringBuilder();
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
