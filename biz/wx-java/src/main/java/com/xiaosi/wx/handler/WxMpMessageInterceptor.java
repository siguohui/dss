package com.xiaosi.wx.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

import java.util.Map;

public interface WxMpMessageInterceptor {

    /**
     * 拦截微信消息
     * @param wxMessage
     * @param context  上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param wxMpService
     * @return  true代表OK，false代表不OK
     */
    public boolean intercept(WxMpXmlMessage wxMessage,
                             Map<String, Object> context,
                             WxMpService wxMpService,
                             WxSessionManager sessionManager);

}
