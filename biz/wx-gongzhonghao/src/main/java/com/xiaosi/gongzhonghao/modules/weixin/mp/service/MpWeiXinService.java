package com.xiaosi.gongzhonghao.modules.weixin.mp.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaosi.gongzhonghao.baseservice.kcaco.daily.domain.DailyPush;
import com.xiaosi.gongzhonghao.baseservice.kcaco.daily.service.DailyPushService;
import com.xiaosi.gongzhonghao.config.LoveConfig;
import com.xiaosi.gongzhonghao.config.api.WeiXinProperties;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Service;

/**
 * Description:
 * 开发文档：https://github.com/Wechat-Group/WxJava/wiki/公众号开发文档
 *
 * @author kcaco
 * @since 2022-08-18 22:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MpWeiXinService {

    private final LoveConfig loveConfig;

    private final WeiXinProperties weiXinProperties;

    private final WxMpService wxMpService;
    private final DailyPushService dailyPushService;

    public void sendDailyDefault() {
        if (StrUtil.hasBlank(weiXinProperties.getUserOpenid(), weiXinProperties.getMsgTempId())) {
            log.error("未配置用户id或者模板id，无法发送！");
        } else {
            sendDaily(weiXinProperties.getUserOpenid(), weiXinProperties.getMsgTempId());
        }
    }


    /**
     * 日常消息推送
     *
     * @param userOpenid        用户id
     * @param messageTemplateId 消息模板id
     */
    public void sendDaily(String userOpenid, String messageTemplateId) {

        DailyPush dailyPushData = dailyPushService.getDailyPushData(loveConfig.getDay(), loveConfig.getGirlBirthday(), loveConfig.getBoyBirthday());
        if (ObjectUtil.isNull(dailyPushData)) {
            throw new RuntimeException("获取推送数据失败!");
        }

        // 此处的 key/value 需和模板消息对应
        List<WxMpTemplateData> wxMpTemplateDataList = Arrays.asList(
                new WxMpTemplateData("date", dailyPushData.getDate(), "#ef5b9c"),
                new WxMpTemplateData("city", dailyPushData.getCity()),
                new WxMpTemplateData("weather", dailyPushData.getWeather(), "#deab8a"),
                //new WxMpTemplateData("windScale", windScale, "#deab8a"),
                new WxMpTemplateData("tempMin", dailyPushData.getTempMin()),
                new WxMpTemplateData("tempMax", dailyPushData.getTempMax()),
                //new WxMpTemplateData("sport", sport),
                new WxMpTemplateData("dress", dailyPushData.getDress(), dailyPushData.getDressColor()),
                new WxMpTemplateData("sunscreen", dailyPushData.getSunscreen(), dailyPushData.getSunscreenColor()),

                new WxMpTemplateData("loveDay", dailyPushData.getLoveDay(), "#f15b6c"),

                new WxMpTemplateData("girlBirthday", dailyPushData.getGirlBirthday()),
                new WxMpTemplateData("boyBirthday", dailyPushData.getBoyBirthday()),

                //new WxMpTemplateData("oneWord", oneWord),
                new WxMpTemplateData("rainbowFart", dailyPushData.getRainbowFart())
        );

        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(userOpenid)
                .templateId(messageTemplateId)
                .data(wxMpTemplateDataList)
                .build();

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            log.error("推送失败：" + e.getMessage());
        }

    }


}
