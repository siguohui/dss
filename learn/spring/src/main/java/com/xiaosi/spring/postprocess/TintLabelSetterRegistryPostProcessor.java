package com.xiaosi.spring.postprocess;

import com.xiaosi.spring.entity.Blue;
import com.xiaosi.spring.entity.Tint;
import com.xiaosi.spring.entity.Yellow;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * BeanFactory后置处理器，用于设置Tint子类bean的label属性。
 * label属性的值会设置为"postProcessBeanFactory_" + beanName。
 */
@Component
public class TintLabelSetterRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if(registry.containsBeanDefinition(Yellow.class.getSimpleName())){
            BeanDefinition yellowDefinition = BeanDefinitionBuilder.genericBeanDefinition(Yellow.class).getBeanDefinition();
            registry.registerBeanDefinition(Yellow.class.getSimpleName(), yellowDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistryPostProcessor.super.postProcessBeanFactory(beanFactory);
    }
}
