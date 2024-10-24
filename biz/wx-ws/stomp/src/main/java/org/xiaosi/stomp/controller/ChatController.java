package org.xiaosi.stomp.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xiaosi.stomp.entity.ChatMessage;
import org.xiaosi.stomp.utils.JsonUtil;
import org.xiaosi.stomp.utils.RedisUtil;

import java.security.Principal;

/**
 * @Author : JCccc
 * @CreateTime : 2020/8/26
 * @Description :
 **/
@Controller
public class ChatController {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 服务端推送给单人的接口
     * @param uid
     * @param content
     */
    @ResponseBody
    @GetMapping("/sendToOne")
    public void sendToOne(@RequestParam("uid") String uid,@RequestParam("content") String content ){

        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.CHAT);
        chatMessage.setContent(content);
        chatMessage.setTo(uid);
        chatMessage.setSender("系统消息");
        rabbitTemplate.convertAndSend("topicWebSocketExchange","topic.public", JsonUtil.parseObjToJson(chatMessage));

    }


    /**
     * 接收 客户端传过来的消息 通过setSender和type 来判别时单发还是群发
     * @param chatMessage
     * @param principal
     */
    @MessageMapping("/chat.sendMessageTest")
    public void sendMessageTest(@Payload ChatMessage chatMessage, Principal principal) {
        try {

            String name = principal.getName();
            chatMessage.setSender(name);
            rabbitTemplate.convertAndSend("topicWebSocketExchange","topic.public", JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     *
     * 消息可以异步处理，@MessageMapping方法可以返回ListenableFuture、CompletableFuture或CompletionStage 类型。
     *
     * 请注意，@SendTo和@SendToUser 注释仅仅是使用SimpMessagingTemplate发送消息的便利方式。
     * 如果必要，对于更高级的场景，@MessageMapping方法可以直接使用SimpMessagingTemplate。
     * 这可以作为返回值的替代方案，或者可能作为返回值的补充。见发送
     * 接收 客户端传过来的消息 上线消息
     * @param chatMessage
     */
    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage) {

        System.out.println("有用户加入到了websocket 消息室" + chatMessage.getSender());
        try {

            System.out.println(chatMessage.toString());
            rabbitTemplate.convertAndSend("topicWebSocketExchange","topic.public", JsonUtil.parseObjToJson(chatMessage));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @ResponseBody
   @GetMapping("/index")
//    @MessageMapping("/topic/msg")
    public String topic(){
        simpMessagingTemplate.convertAndSend("/topic/monitor", "1");
        return "success";
    }

    @SubscribeMapping("/sz")
//    @MessageMapping("/topic/sz")
    public Object monitor(){
        return redisUtil.get("sz");
    }

    /*@SubscribeMapping("/monitor/{userId}")
//    @MessageMapping("/topic/msg")
    public String monitor(@DestinationVariable String userId){  //SimpMessageHeaderAccessor accessor  //Principal p
//        String user = p.getName();
        System.out.println(userId+"---------------------");
//        messagingTemplate.convertAndSendToUser(user, "/monitor", getOps());
        return userId;
    }*/

    /*@SubscribeMapping("/monitor")
    public void init(SimpMessageHeaderAccessor accessor) {
        String user = accessor.getFirstNativeHeader("userid");
        messagingTemplate.convertAndSendToUser(user, "/monitor", getOps());
    }*/

    @MessageExceptionHandler
    public String handleException(Exception exception) {
        return "1";
    }

//    @MessageMapping("/chat")
    public void messageHandling(ChatMessage chatMessage) throws Exception {
//        String destination = "/topic/" + HtmlUtils.htmlEscape(requestMessage.getRoom());

//        String room = HtmlUtils.htmlEscape(requestMessage.getRoom());//htmlEscape  转换为HTML转义字符表示
//        String type = HtmlUtils.htmlEscape(requestMessage.getType());
//        String content = HtmlUtils.htmlEscape(requestMessage.getContent());
//        String userId = HtmlUtils.htmlEscape(requestMessage.getUserId());
//        String questionId = HtmlUtils.htmlEscape(requestMessage.getQuestionId());
//        String createTime = HtmlUtils.htmlEscape(requestMessage.getCreateTime());
//
//        System.out.println( requestMessage.getRoom() );
//        System.out.println( content );
//
//
//
//
//        messagingTemplate.convertAndSend(destination, requestMessage);
    }

    @ResponseBody
    @GetMapping("/send")
    public String sendMsg() {
        this.rabbitTemplate.convertAndSend("hello", "1");
        return "success";
    }
}
