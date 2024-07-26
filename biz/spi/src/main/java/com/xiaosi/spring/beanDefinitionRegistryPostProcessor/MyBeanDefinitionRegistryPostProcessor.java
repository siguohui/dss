package com.xiaosi.spring.beanDefinitionRegistryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

       /* BeanDefinition beanDefinition = registry.getBeanDefinition("rectangle");
        beanDefinition.setScope("prototype");
        beanDefinition.setPrimary(true);*/


        System.out.println("MyBeanDefinitionRegistryPostProcessor: "+registry);

        // 向beanDefinitionMap中注册自定义的beanDefinition对象
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(BeanDemo.class);
        registry.registerBeanDefinition("beanDemo",beanDefinition);

        if (registry.containsBeanDefinition("beanDemo")) {
            registry.removeBeanDefinition("beanDemo");
        }
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor: "+beanFactory);
    }
}
