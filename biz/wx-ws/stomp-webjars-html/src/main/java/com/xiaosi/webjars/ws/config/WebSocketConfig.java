package com.xiaosi.webjars.ws.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.Map;

/**
 * 功能描述：自定义类配置 WebSocket
 * @author wi-gang
 * @date 2022/2/10 11:01 上午
 */

@Configuration
//开启 WebSocket 消息代理
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //定义一个前缀为"/chat"的 endpoint，并开启sockjs支持
        //sockjs可以解决浏览器对 WebSocket 的兼容性问题，客户端通过这里配置的URL来建立 WebSocket 连接
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new DefaultHandshakeHandler() {

                    public boolean beforeHandshake(
                            ServerHttpRequest request,
                            ServerHttpResponse response,
                            WebSocketHandler wsHandler,
                            Map attributes) throws Exception {

                        if (request instanceof ServletServerHttpRequest) {
                            ServletServerHttpRequest servletRequest
                                    = (ServletServerHttpRequest) request;
                            HttpSession session = servletRequest
                                    .getServletRequest().getSession();
                            attributes.put("sessionId", session.getId());
                        }
                        return true;
                    }})
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //设置消息代理的前缀，即如果消息的前缀是"/topic"，就会将消息转发给消息代理（broker），再由消息代理将消息广播给当前连接的客户端。在方法的基础上又增加了 broker 前缀 "/queue"，方便对群发消息和点对点消息进行管理。
//        registry.enableSimpleBroker("/topic","/queue");
        //配置一个或者多个前缀，通过这些前缀过滤出需要被注解方法处理的消息
        //例如：前缀为"/app"的 destination 可以通过@MessageMapping注解的方法处理，而其他destination（例如"topic","/queue"）将被直接交给broker处理。
        registry.setApplicationDestinationPrefixes("/app");

        registry.enableStompBrokerRelay("/exchange","/topic","/queue","/amq/queue")
                .setVirtualHost("/") //对应自己rabbitmq里的虚拟host
                .setRelayHost("localhost")
//                .setRelayPort(Integer.parseInt(rabbitMQProperties.getRabbitmqStompPort()))
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setSystemLogin("guest")
                .setSystemPasscode("guest")
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(4000);
    }
}
