package com.xiaosi.wx.test;

import com.xiaosi.wx.encrypt.annotation.EncryptedTable;
import com.xiaosi.wx.entity.SysUser;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class ReflectionUtilsTest {

    @Test
    public void test1() {
        // ⏹原生Java的方式获类上的注解
        Annotation[] types = SysUser.class.getAnnotations();
        for (Annotation type : types) {
            System.out.println(type);
        }

        Field email = ReflectionUtils.findField(SysUser.class, "email");
        Annotation[] fields = email.getAnnotations();
        for (Annotation annotation : fields) {
            System.out.println(annotation);
        }

        // AnnotationUtils的方式获取指定类上的注解
        EncryptedTable annotation = AnnotationUtils.findAnnotation(SysUser.class, EncryptedTable.class);
        System.out.println(annotation);

        // 通过反射获取aliaPay对象上的pay方法的Method对象
        Method getRoleIds = ReflectionUtils.findMethod(SysUser.class, "getRoleIds");
        // 获取方法上的注解
        EncryptedTable encryptedTable = AnnotationUtils.findAnnotation(getRoleIds, EncryptedTable.class);
        System.out.println(encryptedTable);

        SysUser sysUser = new SysUser();
        // AnnotationUtils的方式获取指定类上的注解
        EncryptedTable encrypted = AnnotationUtils.findAnnotation(sysUser.getClass(), EncryptedTable.class);
        // 获取注解上指定的值
        Object value = AnnotationUtils.getValue(encrypted, "value");
        System.out.println(value);

        // 获取注解上所有的属性值
        EncryptedTable encrypt = AnnotationUtils.findAnnotation(sysUser.getClass(), EncryptedTable.class);
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(encrypt);
        System.out.println(annotationAttributes);

//        getAnnotation: 从某个类获取某个annotation
//        findAnnotation: 从类或方法中查找某个annotation。
//        isAnnotationDeclaredLocally: 验证annotation是否直接注释在类上而不是集成来的。
//        isAnnotationInherited: 验证annotation是否继承于另一个class。
//        getAnnotationAttributes: 获取annotation的所有属性。
//        getValue: 获取指定annotation的值.
//        getDefaultValue: 获取指定annotation或annotation 属性的默认值
    }
}
