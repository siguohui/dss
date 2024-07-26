package com.xiaosi.spring;

import com.xiaosi.spring.beanDefinitionRegistryPostProcessor.BeanDemo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

public class test {

    @Test
    public void test02(){
        //使用spring容器创建对象
        //1、指定spring配置文件的名称
        String config="beans.xml";

        //2、创建表示spring容器的对象，ApplicationContext
        //ApplicationContext 表示spring容器，通过容器获取对象
        //ClassPathXmlApplicationContext 表示从类路径(target/class/)中加载spring的配置文件
        //此时 创建容器，spring创建对象
        ApplicationContext ac=new ClassPathXmlApplicationContext(config);

        //从容器中获取某个对象，需调用对象的方法
        //getBean("配置文件中的bean的id")，返回值是Object
        BeanDemo service= (BeanDemo) ac.getBean("beanDemo");
        //使用spring创建好的对象
        System.out.println(service.getName());
    }

    @Test
    public void test03(){
        String config="beans.xml";
        ApplicationContext ac=new ClassPathXmlApplicationContext(config);

        //获取容器中定义的对象数量
        int count = ac.getBeanDefinitionCount();
        System.out.println("容器中对象数量"+count);
        //获取容器中定义的对象名称
        String[] names = ac.getBeanDefinitionNames();
        for(String name:names){
            System.out.println(name);
        }
    }
}
