package com.xiaosi.webjars.ws.config;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

//@Component
@Slf4j
//@ServerEndpoint("/websocket/{equipmentId}")
public class WebSocketHandler {


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    private Session session;

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketHandler> webSocketUtils = new CopyOnWriteArraySet<>();
    // 用来存在线连接数
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();

    //    private static EquipmentService equipmentService = SpringContextHolder.getBean(EquipmentService.class);
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "equipmentId") String equipmentId) {
        try {
            this.session = session;
            webSocketUtils.add(this);
            sessionPool.put(equipmentId, session);
            sendOneMessage(equipmentId, "");

//            equipmentService.onlineRecord(equipmentId,0);

            log.info("【websocket消息】有新的连接，总数为:" + webSocketUtils.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value = "equipmentId") String equipmentId) {
        try {
            webSocketUtils.remove(this);

//            equipmentService.onlineRecord(equipmentId,1);

            log.info("【websocket消息】连接断开，总数为:" + webSocketUtils.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     * @param
     */
    @OnMessage
    public void onMessage(@PathParam(value = "equipmentId") String equipmentId, String message) {

        log.info("【websocket消息】收到客户端消息:" + message);

        sendOneMessage(equipmentId, message);
    }

    /**
     * 发送错误时的处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误,原因:" + error.getMessage());
        error.printStackTrace();
    }


    /**
     * 推消息给前端
     *
     * @param equipmentId
     * @param message
     * @return
     */
    public static Runnable sendOneMessage(String equipmentId, Object message) {
        Session session = sessionPool.get(equipmentId);
        if (session != null && session.isOpen()) {
            try {
                log.info("【推给前端消息】 :" + message);

                //高并发下，防止session占用期间，被其他线程调用
                synchronized (session) {
                    session.getBasicRemote().sendText(Objects.toString(message));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
