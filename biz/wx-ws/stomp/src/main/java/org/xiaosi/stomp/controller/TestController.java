package org.xiaosi.stomp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaosi.stomp.schedule.ScheduleTask;

/**
 * @author wl
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private final ScheduleTask scheduleTask;

    @Autowired
    public TestController(ScheduleTask scheduleTask) {
        this.scheduleTask = scheduleTask;
    }

    @GetMapping("/updateCron")
    public String updateCron(String cron) {
        log.info("new cron :{}", cron);
        scheduleTask.setCron(cron);
        return "ok";
    }
}
