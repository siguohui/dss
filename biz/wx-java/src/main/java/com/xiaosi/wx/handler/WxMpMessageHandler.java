package com.xiaosi.wx.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

public interface WxMpMessageHandler {

    /**
     *
     * @param wxMessage
     * @param context  上下文，如果同一个路由规则内的handler或interceptor之间有信息要传递，可以用这个
     * @param wxMpService
     * @return xml格式的消息，如果在异步规则里处理的话，可以返回null
     */
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context,
                                    WxMpService wxMpService,
                                    WxSessionManager sessionManager);

}
