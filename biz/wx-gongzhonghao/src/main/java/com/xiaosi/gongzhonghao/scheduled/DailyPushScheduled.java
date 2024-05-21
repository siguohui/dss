package com.xiaosi.gongzhonghao.scheduled;

import cn.hutool.core.date.DateUtil;
import com.xiaosi.gongzhonghao.config.DefaultConfig;
import com.xiaosi.gongzhonghao.modules.weixin.work.service.DailyPushCpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


/**
 * Description: 日常推送定时任务
 *
 * @author kcaco
 * @since 2022-08-19 3:24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DailyPushScheduled {
    private final DailyPushCpService dailyPushCpService;
    private final DefaultConfig defaultConfig;

    // 秒 分  时 日 月  周几
    // 0  *  *  *  *  MON-FRI

    /**
     * 每天早上9点开始——默认企业微信
     */
    @Scheduled(cron = "10 35 16 * * ?")
    public void sendDailyMorning() {
        log.info("开始执行早上定时日常推送，当前时间：" + DateUtil.now());

        String push = defaultConfig.getPush();
        switch (push) {
            case "work":
                // 企业微信
                dailyPushCpService.sendDailyPushWorkWeiXin();
                break;
            case "mp":
                // 微信公众号
                dailyPushCpService.sendDailyPushMpWeiXin();
                break;
            default:
                // 企业微信
                dailyPushCpService.sendDailyPushWorkWeiXin();
        }
    }

    ///**
    // * 每天晚上6点开始
    // */
    //@Scheduled(cron = "0 0 18 * * ?")
    //public void sendDailyNight() {
    //    log.info("开始执行晚上定时日常推送，当前时间：" + DateUtil.now());
    //    dailyPushCpService.sendDailyPush();
    //}

}
