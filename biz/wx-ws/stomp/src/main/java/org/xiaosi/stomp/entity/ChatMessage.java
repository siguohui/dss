package org.xiaosi.stomp.entity;

import lombok.Data;

/**
 * @Author : JCccc
 * @CreateTime : 2020/8/26
 * @Description :
 **/
@Data
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String to;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
