package com.xiaosi.wx.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DssDataPermission {

//    PermissionFieldEnum field();
//    PermissionFieldEnum vendorfield();
//    BillTypeEnum billType();
//    OperationTypeEnum operation();
}
