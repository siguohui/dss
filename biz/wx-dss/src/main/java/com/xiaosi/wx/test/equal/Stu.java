package com.xiaosi.wx.test.equal;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;

@Data
@Builder
@EqualsAndHashCode(callSuper = true,exclude = "age")
public class Stu extends Person{

    private String name;
    public Integer age;

    public static void main(String[] args) {
        Field[] declaredFields = Stu.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            System.out.println(declaredFields[i]);
        }

        System.out.println("------------------------");

        Field[] fields = Stu.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i]);
        }


        Class<?> stuClass = Stu.class;
        System.out.println(stuClass.getFields().length);

        Class<Stu> stuClass1 = Stu.class;
        System.out.println(stuClass1.getFields().length);
    }
}
