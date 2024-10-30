package com.xiaosi.webjars.ws.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.xiaosi.webjars.ws.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ChatMessageHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> allClients;//用于缓存所有的用户和连接之间的关系

    static {
        allClients = new ConcurrentHashMap();//初始化连接
    }

    /**
     * 当和用户成功建立连接的时候会调用此方法,在此方法内部应该保存连接
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("建立连接成功");
        String name = (String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME);//将在拦截器中保存的用户的名字取出来,然后作为 key 存到 map 中
        if (name != null) {
            allClients.put(name, session);//保存当前的连接和用户之间的关系
        }
        // 这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户

    }

    /**
     * 收到消息的时候会触发该方法
     * @param session 发送消息的用户的 session
     * @param message  发送的内容
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //此处请根据自己的具体业务逻辑做处理
        JSONObject jsonObject= JSONObject.parseObject(new String(message.asBytes()));//将用户发送的消息转换为 json,实际开发中请根据自己的需求处理
        String toName = jsonObject.getString("toName");//获取数据中的收消息人的名字
        String content = jsonObject.getString("content");//获取到发送的内容
        String fromName = (String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME);//获取当前发送消息的人的名字
        content = "收到来自:" +fromName+ "的消息,内容是:" + content;
        //拼接内容转发给接收者,实际开发中请参考自己的需求做处理
        TextMessage textMessage = new TextMessage(content);//将内容转换为 TextMessage
        sendMessageToUser(toName,textMessage);// 发送给指定的用户
        //sendMessageToUsers(message);//给所有人发送
        //super.handleTextMessage(session, message);
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        WebSocketSession webSocketSession = allClients.get(userName);//根据接收方的名字找到对应的连接
        if (webSocketSession != null&& webSocketSession.isOpen()) {//如果没有离线,如果离线,请根据实际业务需求来处理,可能会需要保存离线消息
            try {
                webSocketSession.sendMessage(message);//发送消息
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 给所有在线用户发送消息,此处以文本消息为例子
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (Map.Entry<String, WebSocketSession> webSocketSessionEntry : allClients.entrySet()) {//获取所有的连接

            WebSocketSession session = webSocketSessionEntry.getValue();//找到每个连接
            if (session != null&& session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 出现异常的时候
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String name = (String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME);
        if (session.isOpen()) {
            session.close();
        }
        log.debug("连接关闭");
        allClients.remove(name);//移除连接
    }

    /**
     * 连接关闭后
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.debug("连接关闭");
        String name = (String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME);//找到用户对应的连接
        allClients.remove(name);//移除
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
