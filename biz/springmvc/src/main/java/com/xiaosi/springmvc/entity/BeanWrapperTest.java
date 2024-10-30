package com.xiaosi.springmvc.entity;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class BeanWrapperTest {

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("1");
        User user1 = new User();
        user1.setUsername("2");
        // 获取一个对象的beanWrapper对象
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(user);
        BeanWrapper bw1 = PropertyAccessorFactory.forBeanPropertyAccess(user1);
        //设置值
        bw.setPropertyValue("passworld", "1");
        bw1.setPropertyValue("passworld", "2");
        System.out.println(user); // 输出 516
        System.out.println(user1);// 输出 sx516
    }
}
