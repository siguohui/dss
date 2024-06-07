package com.xiaosi.wx.hutool;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;

import java.util.Date;

public class CronUtilTest {

    public static void main(String[] args) {
        Task task = new Task() {
            @Override
            public void execute() {
                System.out.println("execute task at " + new Date());
            }
        };
        CronUtil.schedule("*/5 * * * * ?", task);  //每隔5秒执行一次任务  */5 * * * * ?
        CronUtil.start();  // 启动定时任务
    }

}
