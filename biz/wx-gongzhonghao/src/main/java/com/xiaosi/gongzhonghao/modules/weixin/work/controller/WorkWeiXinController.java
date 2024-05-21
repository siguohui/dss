package com.xiaosi.gongzhonghao.modules.weixin.work.controller;

import com.xiaosi.gongzhonghao.modules.weixin.work.service.DailyPushCpService;
import com.xiaosi.gongzhonghao.modules.weixin.work.service.WorkWeiXinService;
import com.xiaosi.gongzhonghao.scheduled.DailyScheduled;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-19 17:48
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/workWeiXin")
public class WorkWeiXinController {

    private final WorkWeiXinService workWeiXinService;
    @Autowired
    private DailyPushCpService dailyPushCpService;

    private final DailyScheduled dailyScheduled;

    /**
     * 发送文本消息
     *
     * @param message
     */
    @GetMapping("/sendTextMessage")
    public void sendTextMessage(@RequestParam("message") String message) {
        workWeiXinService.sendTextMessage(message);
    }

    /**
     * 测试每日推送
     */
    @GetMapping("/sendDailyPush")
    public void sendDailyPush() {
        dailyPushCpService.sendDailyPushWorkWeiXin();
    }

    /**
     * 测试每日推送
     */
    @GetMapping("/test")
    public void test() {
        //dailyScheduled.sendDailyMorning();
    }
}
