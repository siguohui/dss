package com.xiaosi.gongzhonghao.modules.weixin.work.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpMessageService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpMessageServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.stereotype.Service;

/**
 * Description: 企业微信服务
 *
 * @author kcaco
 * @since 2022-08-19 17:36
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkWeiXinService {

    private final WxCpService wxCpService;


    /**
     * 发送文本消息
     */
    public void sendTextMessage(String message) {
        WxCpMessageService wxCpMessageService = new WxCpMessageServiceImpl(wxCpService);
        WxCpDefaultConfigImpl storage = new WxCpDefaultConfigImpl();
        WxCpMessage wxCpMessage = WxCpMessage
                .TEXT()
                .toUser("@all")
                .content(message)
                .build();
        try {
            wxCpMessageService.send(wxCpMessage);
        } catch (WxErrorException e) {
            log.error("发送消息失败——" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
