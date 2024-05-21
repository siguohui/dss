package com.xiaosi.gongzhonghao.modules.weixin.work.service;

import cn.hutool.core.util.StrUtil;
import com.xiaosi.gongzhonghao.baseservice.kcaco.daily.domain.DailyPush;
import com.xiaosi.gongzhonghao.baseservice.kcaco.daily.service.DailyPushService;
import com.xiaosi.gongzhonghao.common.utils.KcacoUtil;
import com.xiaosi.gongzhonghao.config.LoveConfig;
import com.xiaosi.gongzhonghao.modules.weixin.mp.service.MpWeiXinService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-20 17:08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DailyPushCpService {
    private final LoveConfig loveConfig;

    private final DailyPushService dailyPushService;
    private final WorkWeiXinService workWeiXinService;
    private final MpWeiXinService mpWeiXinService;


    /**
     * 发送每日推送——企业微信
     */
    public void sendDailyPushWorkWeiXin() {
        // 获取消息模板
        String messageTemplate = dailyPushService.getLoveMsgTemp();

        // 获取推送数据
        DailyPush dailyPush = dailyPushService.getDailyPushData(loveConfig.getDay(), loveConfig.getGirlBirthday(), loveConfig.getBoyBirthday());

        // 填充消息模板
        Map<String, String> objectFieldValueMap = KcacoUtil.getObjectFieldValueMap(DailyPush.class, dailyPush);
        String message = StrUtil.format(messageTemplate, objectFieldValueMap);

        // 发送消息
        workWeiXinService.sendTextMessage(message);
    }

    /**
     * 发送每日推送——微信公众号
     */
    public void sendDailyPushMpWeiXin() {
        mpWeiXinService.sendDailyDefault();
    }

}
