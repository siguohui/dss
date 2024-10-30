package com.xiaosi.stomp.config;

import com.xiaosi.stomp.server.UserHandleShake;
import com.xiaosi.stomp.utils.AuthoritiesConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
@EnableWebSocketMessageBroker //@EnableWebSocketMessageBroker注解来启用WebSocket消息代理功能
public class WebsocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    //在registerStompEndpoints方法中，
    // 我们使用addEndpoint方法来添加WebSocket端点，这里我们将WebSocket端点设置为"/ws"，
    // 并使用withSockJS方法启用SockJS支持，以便在不支持WebSocket的浏览器上进行通信。
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint用来建立ws连接的
        registry.addEndpoint("/gs-guide-websocket", "/test2")
//                .setHandshakeHandler(userHandleShake())
//                .setHandshakeHandler(defaultHandshakeHandler())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override//使用configureMessageBroker方法来配置消息代理的相关参数，使用registerStompEndpoints方法来注册Stomp协议的WebSocket端点。
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // to enable a simple memory-based message broker to carry
        // the greeting messages back to the client on destinations prefixed with "/topic".
        // broker广播器，定义一个广播器前缀，前端（客户端）可以向指定的channel发起订阅，当后端通过@sendTo 或者convertAndSend 可以向指定
        //通道发消息，订阅了该通道的客户端可以收到消息
        //enableSimpleBroker方法来配置消息代理的地址，这里我们将消息代理地址设置为"/topic"，表示所有以"/topic"开头的消息都会被路由到消息代理上。
        registry.enableSimpleBroker("/topic", "/chat");//.setHeartbeatValue(new long[]{10000,10000});
        // designates the "/app" prefix for messages that are
        // bound for @MessageMapping-annotated methods.
        // This prefix will be used to define all the message mappings;
        // ws接口定义前缀，后端定义了接口，使用@MessageMapping 前端可以请求该接口并传数据
        // 总结  broker可以用来向客户端发送数据，destination可以用来向服务器发送数据 ，区别在于，客户端得先发起订阅
        //方法用于设置应用程序的目的地前缀，这里我们将其设置为"/app"，表示所有以"/app"开头的消息都会被路由到控制器上。
        registry.setApplicationDestinationPrefixes("/app");
//        registry.setUserDestinationPrefix("/user");
    }

//    @Bean
//    public UserHandleShake userHandleShake() {
//        return new UserHandleShake();
//    }

    private DefaultHandshakeHandler defaultHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                Principal principal = request.getPrincipal();
                if (principal == null) {
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
                    principal = new AnonymousAuthenticationToken("WebsocketConfiguration", "aaaa", authorities);
                }
                return principal;
            }
        };
    }




    /*@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors();//拦截器
    }*/

   /* 除了上述示例中提到的方法之外，WebSocketMessageBrokerConfigurer 接口还提供了其他方法，
    例如 configureWebSocketTransport()、configureClientOutboundChannel() 等。通过实现这些方法，我们可以对 WebSocket 消息代理进行更细粒度的配置。*/

    /*@Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add() //转换器
        return true;
    }*/
}
