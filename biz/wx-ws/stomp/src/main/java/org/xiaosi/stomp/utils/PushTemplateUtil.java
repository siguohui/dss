package org.xiaosi.stomp.utils;

import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

public class PushTemplateUtil {

    @SneakyThrows
    public void sendMessage(String openId,WxMpService weixinService) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId("L5zBq2BRM5PsxNProp-WIoPzobnNntoTsBy2-GHrFyE")
                .url("https://blog.csdn.net/A_yonga?spm=1000.2115.3001.5343")
                .build();

        templateMessage
                .addData(new WxMpTemplateData("first", "微信搜一颗星宇宙公众号体验"));

        weixinService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    }
}
