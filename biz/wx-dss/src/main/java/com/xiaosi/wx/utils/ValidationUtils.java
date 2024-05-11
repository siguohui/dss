package com.xiaosi.wx.utils;

import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

public class ValidationUtils {

    public static Validator getValidator(){
        return validator;
    }

    static Validator validator;
    static{
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator=validatorFactory.getValidator();
    }
}
