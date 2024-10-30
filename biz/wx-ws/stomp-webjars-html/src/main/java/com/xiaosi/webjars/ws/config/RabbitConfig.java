package com.xiaosi.webjars.ws.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(RabbitTemplate.class)
public class RabbitConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");//消息队列名随便取
    }

    @Bean
    public MessageConverter messageConverter(){
        //使用json序列化发送消息
        return new Jackson2JsonMessageConverter();
    }

}
