package com.xiaosi.webjars.ws.config;

import com.xiaosi.webjars.ws.interceptor.ChatHandshakeInterceptor;
import com.xiaosi.webjars.ws.interceptor.ChatMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//@Configuration //声明为配置文件
//@EnableWebSocket//启用 websocket
public class WsConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        System.out.println("初始化路径拦截");//指定所有/websocket开头的路径会被 websocket 拦截,设置处理器和拦截器
        webSocketHandlerRegistry.addHandler(chatMessageHandler(),"/websocket/*").addInterceptors(new ChatHandshakeInterceptor());
    }

    /**
     * 创建处理器
     * @return
     */
    @Bean
    public TextWebSocketHandler chatMessageHandler(){
        System.out.println("创建 handler");
        return new ChatMessageHandler();
    }
}
