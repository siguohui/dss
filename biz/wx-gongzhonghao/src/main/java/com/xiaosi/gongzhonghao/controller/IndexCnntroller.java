package com.xiaosi.gongzhonghao.controller;

import com.xiaosi.gongzhonghao.config.DefaultConfig;
import com.xiaosi.gongzhonghao.modules.weixin.work.service.DailyPushCpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexCnntroller {
    private final DailyPushCpService dailyPushCpService;
    private final DefaultConfig defaultConfig;

    @GetMapping("/push")
    public String getindex() {
        dailyPushCpService.sendDailyPushWorkWeiXin();
//        dailyPushCpService.sendDailyPushMpWeiXin();
        return "sucess";
    }
}
