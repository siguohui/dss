package com.xiaosi;

import com.xiaosi.dao.HelloService;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;

import java.util.Set;

//容器启动的时候会将@HandlesTypes 指定的这个类型下面的子类（实现类，子接口等）传递过来；
//传入感兴趣的类型；
@HandlesTypes(value={HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    /**
     * 应用启动的时候，会运行onStartup方法；
     *
     * Set<Class<?>> arg0：感兴趣的类型的所有子类型；
     * ServletContext arg1:代表当前Web应用的ServletContext；一个Web应用一个ServletContext；
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {

        System.out.println("感兴趣的类型：");
        for (Class<?> clazz : c) {
            System.out.println(clazz);
        }
    }

}
