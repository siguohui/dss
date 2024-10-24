package org.xiaosi.stomp.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RedisMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageStr = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println(messageStr+"---------------------");
        /**
         * 根据实际情况处理消息
         */
       /* List<WebsocketRes> websocketRes = JSONArray.parseArray(messageStr, WebsocketRes.class);
        String equipmentId = "";
        List<WebsocketRes> websocketResList = new ArrayList<>();
        for(WebsocketRes res : websocketRes){
            equipmentId = res.getEquipmentId();
            res.setEquipmentId(null);
            websocketResList.add(res);
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(websocketResList);
        WebSocketHandler.sendOneMessage(equipmentId,jsonString);*/
    }
}
