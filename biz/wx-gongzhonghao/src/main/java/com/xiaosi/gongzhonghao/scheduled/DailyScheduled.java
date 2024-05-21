package com.xiaosi.gongzhonghao.scheduled;

import com.xiaosi.gongzhonghao.baseservice.qweather.QweatherService;
import com.xiaosi.gongzhonghao.common.utils.RedisUtil;
import com.xiaosi.gongzhonghao.modules.weixin.work.service.WorkWeiXinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description: 日常定时任务
 *
 * @author kcaco
 * @since 2022-08-20 22:24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DailyScheduled {

    private final QweatherService qweatherService;
    private final WorkWeiXinService workWeiXinService;
    private final RedisUtil redisUtil;

    ///**
    // * 天气预警
    // * 每小时请求一次
    // */
    //@Scheduled(cron = "0 0 0/1 * * ?")
    //public void sendDailyMorning() {
    //    log.info("开始执行天气预警任务，当前时间：" + DateUtil.now());
    //    QweatherDisasterWarnRes response = qweatherService.getWeatherDisasterWarning(new QweatherRequest());
    //    if (StrUtil.equals(response.getCode(), "200")) {
    //        List<QweatherDisasterWarnRes.Warning> warningList = response.getWarning();
    //        if (CollUtil.isNotEmpty(warningList)) {
    //            // 从缓存中查取数据，过滤出新的消息
    //            Map<Object, Object> cacheData = redisUtil.hmget(RedisConstant.QWEATHER_WARNING_KEY);
    //            if (CollUtil.isNotEmpty(cacheData)) {
    //                warningList = warningList.stream()
    //                        .filter(warning -> !cacheData.containsKey(warning.getId()))
    //                        .collect(Collectors.toList());
    //            }
    //
    //            if (CollUtil.isNotEmpty(warningList)) {
    //                // 发送信息
    //                StringBuilder stringBuilder = new StringBuilder();
    //                stringBuilder.append("⚠️ 注意天气变化！").append("\n");
    //                for (QweatherDisasterWarnRes.Warning warning : warningList) {
    //                    String message = warning.getText();
    //                    stringBuilder.append(message).append("\n");
    //                }
    //                stringBuilder.append("详情见：").append(response.getFxLink());
    //                workWeiXinService.sendTextMessage(stringBuilder.toString());
    //
    //                // 存放到redis，重置2天有效期
    //                for (QweatherDisasterWarnRes.Warning warning : warningList) {
    //                    redisUtil.hset(RedisConstant.QWEATHER_WARNING_KEY, warning.getId(), warning.getText());
    //                }
    //                redisUtil.expire(RedisConstant.QWEATHER_WARNING_KEY, 172800L);
    //            }
    //        }
    //    } else {
    //        log.error("执行天气预警任务失败, 错误代码：" + response.getCode());
    //    }
    //}

}
