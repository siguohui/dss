package com.xiaosi.design.proxy;

/**
 * JDK 中的应用：
 *
 * 动态代理 java.lang.reflect.Proxy
 * RMI（远程方法调用）
 * Spring 中的应用：
 *
 * AOP（面向切面编程）广泛使用代理模式。
 *
 *
 * 代理模式通过代理对象控制对目标对象的访问，常用于权限控制、日志记录等场景。
 */
public class ServiceProxy implements Service {
    private RealService realService;

    @Override
    public void execute() {

        System.out.println("Checking permissions");
        if (realService == null) {
            realService = new RealService();
        }

        realService.execute();
    }
}
