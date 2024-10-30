package com.xiaosi.webjars.ws.listener;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收消息
 */
@Component
@RequiredArgsConstructor
public class StoreListener {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "store.addFavorite.success.queue", durable = "true"), // 队列 起名规则（服务名+业务名+成功+队列），durable持久化
            exchange = @Exchange(name = "addFavorite.direct"), // 交换机名称，交换机默认类型就行direct，所以不用配置direct
            key = "addFavorite.success" // 绑定的key
    ))
    public void listenAddFavoriteCountsSuccess(Long storeId){
        System.out.println(storeId);
    }

}
