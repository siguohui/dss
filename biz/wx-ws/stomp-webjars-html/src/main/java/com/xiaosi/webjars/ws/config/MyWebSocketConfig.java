//package com.xiaosi.webjars.ws.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
///**
// * 开启WebSocket代理
// * 也就是开启stomp协议来传输基于消息的broker消息
// * 像rabbitmq的模式一样，有发布，订阅，主题的概念
// */
//@EnableWebSocketMessageBroker
//public class MyWebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    /**
//     * 注册端点，发布或者订阅消息的时候需要连接此端点,(客户端将通过这里配置的 URL 来建立 WebSocket 连接)
//     */
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//
//        registry.addEndpoint("/endpoint-websocket")
//                //setAllowedOrigins 非必须，*表示允许其他域进行连接
//                .setAllowedOrigins("*")
//                //withSockJS  表示开始sockejs支持,可以解决浏览器对 WebSocket 的兼容性问题
//                .withSockJS();
//    }
//
//
//    /**
//     * 配置消息代理(中介)
//     * enableSimpleBroker 或 enableStompBrokerRelay 服务端推送给客户端的路径前缀
//     * setApplicationDestinationPrefixes  客户端发送数据给服务器端的一个前缀
//     */
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//
//        //**************这里只能为这三种值**************
//        registry.enableStompBrokerRelay("/topic","/queue","/exchange")
//                .setRelayHost("localhost")
//                //这里这里是stomp的端口，rabbitmq先要安装开启stomp的插件
//                .setRelayPort(61613)
//                .setClientLogin("guest")
//                .setClientPasscode("guest")
//                .setVirtualHost("/");
//        registry.setApplicationDestinationPrefixes("/server_1", "/server_2");
//
//    }
//}
