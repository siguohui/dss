package com.xiaosi.wx.handler.impl;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyTextHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {

        // 接收消息内容
        String inContent = wxMessage.getContent();

        // 响应的消息内容
        String outContent;
        // 根据不同的关键字回复消息
        if (inContent.contains("hello")) {
            outContent = "https://blog.51cto.com/u_16213580/9891831";
        } else if (inContent.contains("java")) {
            outContent = "hello java";
        } else if (inContent.contains("***")) {
            outContent = "请文明用语";
        } else {
            outContent = "服务繁忙,暂时不能回复";
        }

        // 构造响应消息对象
        return WxMpXmlOutMessage.TEXT().content(outContent).fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
    }
}
