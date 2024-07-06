package com.xiaosi.wx.handler.config;

import com.xiaosi.wx.handler.impl.MyTextHandler;
import com.xiaosi.wx.handler.impl.MyTextInterceptor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageRouterConfig {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private MyTextHandler textHandler;
    @Autowired
    private MyTextInterceptor textInterceptor;


    @Bean
    public WxMpMessageRouter messageRouter() {
        // 创建消息路由
        final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 添加一个同步处理文本消息的路由规则 同时添加interceptor、handler
        router.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).interceptor(textInterceptor).handler(textHandler).end();
        return router;
    }
}
