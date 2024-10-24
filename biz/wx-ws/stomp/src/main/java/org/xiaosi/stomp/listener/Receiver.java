package org.xiaosi.stomp.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "hello")
public class Receiver {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @RabbitHandler
    public void process(String context) throws IOException {
        System.out.println("Receiver : " + context);

        /*RequestMessage mqTask = new RequestMessage(  );
        BeanUtils.copyProperties( JsonUtils.jsonToObject( context,RequestMessage.class ),mqTask );

        if (Objects.equals( mqTask.getType(), "2" )) {
            String destination = "/topic/" +mqTask.getRoom();

            messagingTemplate.convertAndSend( destination, mqTask);
        }*/

    }

}
