package com.xiaosi.stomp.config;

import com.xiaosi.stomp.server.UserHandleShake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**、
 *
 * stomp协议方式
 * 添加websocket配置
 * 实现WebSocketMessageBrokerConfigurer并实现相关方法
 * @ClassName: WebsocketBrokerConfig
 * @Description:
 * @Author: 88578
 * @Date: 2022/5/18 14:44
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint用来建立ws连接的
        registry.addEndpoint("/gs-guide-websocket", "/test2")
                .setHandshakeHandler(userHandleShake())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // to enable a simple memory-based message broker to carry
        // the greeting messages back to the client on destinations prefixed with "/topic".
        // broker广播器，定义一个广播器前缀，前端（客户端）可以向指定的channel发起订阅，当后端通过@sendTo 或者convertAndSend 可以向指定
        //通道发消息，订阅了该通道的客户端可以收到消息
        registry.enableSimpleBroker("/topic", "/chat");
        // designates the "/app" prefix for messages that are
        // bound for @MessageMapping-annotated methods.
        // This prefix will be used to define all the message mappings;
        // ws接口定义前缀，后端定义了接口，使用@MessageMapping 前端可以请求该接口并传数据
        // 总结  broker可以用来向客户端发送数据，destination可以用来向服务器发送数据 ，区别在于，客户端得先发起订阅
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Bean
    public UserHandleShake userHandleShake() {
        return new UserHandleShake();
    }
}
