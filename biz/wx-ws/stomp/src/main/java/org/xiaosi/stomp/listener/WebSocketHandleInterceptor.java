package org.xiaosi.stomp.listener;

import com.sun.security.auth.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.security.Principal;
import java.util.LinkedList;
import java.util.Map;

public class WebSocketHandleInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                Object name = ((Map) raw).get("username"); //取出客户端携带的参数
                System.out.println(name);
                if (name instanceof LinkedList) {
                    // 设置当前访问的认证用户
                    accessor.setUser(new UserPrincipal(((LinkedList) name).get(0).toString()));
                }
            }

            /*String username = accessor.getFirstNativeHeader("username");
            if (StringUtils.isEmpty(username)) return null;
            // 绑定user
            Principal principal = new UserPrincipal(username);
            accessor.setUser(principal);*/
        }
        return message;
    }
}
