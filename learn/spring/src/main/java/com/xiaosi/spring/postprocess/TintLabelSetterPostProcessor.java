package com.xiaosi.spring.postprocess;

import com.xiaosi.spring.entity.Tint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * TintLabelSetterPostProcessor类是一个BeanPostProcessor的实现，
 * 它为类型为Tint的bean设置'label'属性。该属性的值将被设置为"postProcessAfterInitialization_"加上bean的名称。
 * 这里是一个postProcessAfterInitialization方法，它会在bean初始化后，但在返回给调用者之前执行。
 */
@Component
public class TintLabelSetterPostProcessor implements BeanPostProcessor {

    /**
     * 对bean进行后初始化处理。如果bean是Tint类型，它的'label'属性将被设置。
     *
     * @param bean 将要处理的bean对象。
     * @param beanName bean的名称。
     * @return 可能已经修改过的bean。
     * @throws BeansException 如果在处理过程中出现错误。
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Tint) {
            Tint tint = (Tint) bean;
            tint.setLabel("postProcessAfterInitialization_" + beanName);
        }
        return bean;
    }
}
