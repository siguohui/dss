package com.xiaosi.gongzhonghao.modules.weixin.mp.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import java.util.Date;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-18 23:53
 */
public class LoveUtil {

    /**
     * 下一个生日天数
     */
    public static long getBetweenBirthday(String birthday) {
        DateTime dateTime = DateUtil.parseDate(birthday);
        Date now = new Date();

        int currentYear = DateUtil.year(now);
        int currentMonth = DateUtil.month(now) + 1;
        int currentDay = DateUtil.dayOfMonth(now);
        int month = DateUtil.month(dateTime) + 1;
        int day = DateUtil.dayOfMonth(dateTime);

        if (currentMonth >= month && currentDay >= day) {
            currentYear = currentYear + 1;
        }

        String nowBirthday = currentYear +
                "-" +
                (month > 9 ? String.valueOf(month) : "0" + month) +
                "-" +
                (day > 9 ? String.valueOf(day) : "0" + day);
        return DateUtil.between(DateUtil.parseDate(nowBirthday), now, DateUnit.DAY) + 1;
    }

    /**
     * 获取生日消息
     */
    public static String getBetweenBirthdayMeg(long day, String sex) {
        String message = "";

        if (NumberUtil.equals(day, 0L)) {
            message = "祝" + sex + "生日快乐！🎉🎉🎉";
        } else if (NumberUtil.equals(day, 1L)) {
            message = "明天就是" + sex + "的🎂，准备生日🎁了吗？";
        } else if (day < 5L) {
            message = "距离" + sex + "的🎂还有" + day + "天，赶快准备生日🎁吧!";
        } else {
            message = "距离" + sex + "的🎂还有" + day + "天";
        }

        return message;
    }

}
