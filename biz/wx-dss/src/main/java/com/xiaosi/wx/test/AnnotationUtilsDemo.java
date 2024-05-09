package com.xiaosi.wx.test;

import java.util.Map;

import com.xiaosi.wx.test.annotation.SingletonComponent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@ComponentScan
public class AnnotationUtilsDemo {

    private static void annotationUtilsDemo() {
        //获取类注解
        SingletonComponent singletonComponentAnnocation = AnnotationUtils.findAnnotation(SimpleSingletonService.class, SingletonComponent
                .class);

        System.out.println("@SingletonComponent : " + singletonComponentAnnocation);
        System.out.println("@SingletonComponent value: " + AnnotationUtils.getValue(singletonComponentAnnocation, "value"));


        System.out.println("----------------------------------------------");

        Scope scopeAnnocation = AnnotationUtils.findAnnotation(SimpleSingletonService.class, Scope.class);

        System.out.println("@Scope : " + scopeAnnocation);
        System.out.println("@Scope value: " + AnnotationUtils.getValue(scopeAnnocation, "scopeName"));

        System.out.println("----------------------------------------------");

        //获取@AliasFor Marge 后的注解，直接调用 AnnotationUtils的方法不会组合@AliasFor的值
        Component componentAnnocation = AnnotatedElementUtils.findMergedAnnotation(SimpleSingletonService.class, Component.class);

        System.out.println("@Component : " + componentAnnocation);
        System.out.println("@Component value: " + AnnotationUtils.getValue(componentAnnocation, "value"));

    }

    //获取所有Annotation注解的类对象，获取Meta Annotation
    private static void getAllAnnocations() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();) {
            context.register(AnnotationUtilsDemo.class);
            context.refresh();

            //@SingletonComponent 继承了 @Component 所以存在实例
            Map<String, Object> beans = context.getBeansWithAnnotation(SingletonComponent.class);
            for (Object bean : beans.values()) {
                System.out.println("bean : " + bean);
                Component componentAnnocation = AnnotatedElementUtils.findMergedAnnotation(bean.getClass(), Component.class);
                System.out.println(componentAnnocation);
            }
        }

    }

    public static void main(String[] args) {
        AnnotationUtilsDemo.annotationUtilsDemo();
        System.out.println("----------------------------------------------");
        AnnotationUtilsDemo.getAllAnnocations();
    }
}
