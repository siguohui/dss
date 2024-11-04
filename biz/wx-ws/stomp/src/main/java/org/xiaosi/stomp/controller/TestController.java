package org.xiaosi.stomp.controller;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaosi.stomp.schedule.ScheduleTask;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        Set<Integer> collect = list.stream().filter(Objects::nonNull).collect(Collectors.toSet());

        System.out.println(collect.isEmpty());


        System.out.println(CollUtil.max(list.stream().filter(Objects::nonNull).collect(Collectors.toSet())));


        Locale locale = new Locale("zh","CN");
        NumberFormat instance = NumberFormat.getCurrencyInstance(locale);
        double money = 15673.3363;
        System.out.println(instance.format(money));

        Locale locale1 = new Locale("en","US");
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,locale1);
        System.out.println(dateFormat.format(date));


        List<String> l = Lists.newArrayList();
        l.add("张三");
        l.add("张三");
        l.add("张三");
        l.add("李四");
        l.add("李四");
        l.add("王五");

        List<String> newList = l.stream().distinct().toList();

        System.out.println(newList);
    }


}
