package com.xiaosi.spring.postprocess;

import com.xiaosi.spring.entity.Tint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/**
 * BeanFactory后置处理器，用于设置Tint子类bean的label属性。
 * label属性的值会设置为"postProcessBeanFactory_" + beanName。
 */
@Component
public class TintLabelSetterFactoryPostProcessor implements BeanFactoryPostProcessor {

    /**
     * 在所有BeanDefinition加载完成之后，但bean实例化之前，设置label属性。
     *
     * @param beanFactory 可配置的bean工厂，可以操作BeanDefinition。
     * @throws BeansException 处理过程中的异常。
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 遍历所有bean的名字
        /*for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

            // 检查bean的类名是否非空，且其父类是Tint
            if (beanDefinition.getBeanClassName() != null &&
                    ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), this.getClass().getClassLoader())
                            .getSuperclass().equals(Tint.class)) {

                // 添加或更新（如果属性已存在）label属性的值
                beanDefinition.getPropertyValues().add("label", "postProcessBeanFactory_" + beanName);
            }
        }*/
    }
}
