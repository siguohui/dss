package com.xiaosi.gongzhonghao.baseservice.kcaco.daily.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaosi.gongzhonghao.baseservice.kcaco.daily.domain.DailyPush;
import com.xiaosi.gongzhonghao.baseservice.oneword.OneWordReq;
import com.xiaosi.gongzhonghao.baseservice.oneword.OneWordRes;
import com.xiaosi.gongzhonghao.baseservice.oneword.OneWordService;
import com.xiaosi.gongzhonghao.baseservice.qweather.QweatherService;
import com.xiaosi.gongzhonghao.baseservice.qweather.domain.request.QweatherRequest;
import com.xiaosi.gongzhonghao.baseservice.qweather.domain.response.QweatherCityInfoRes;
import com.xiaosi.gongzhonghao.baseservice.qweather.domain.response.QweatherDayWeatherRes;
import com.xiaosi.gongzhonghao.baseservice.qweather.domain.response.QweatherLifeIndexRes;
import com.xiaosi.gongzhonghao.baseservice.qweather.domain.response.QweatherRealTimeWeatherRes;
import com.xiaosi.gongzhonghao.baseservice.tian.TianRes;
import com.xiaosi.gongzhonghao.baseservice.tian.TianService;
import com.xiaosi.gongzhonghao.modules.weixin.mp.util.LoveUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.xiaosi.gongzhonghao.modules.weixin.mp.common.CommonConstant.*;
import static com.xiaosi.gongzhonghao.modules.weixin.mp.common.CommonConstant.WARING_COLOR;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-20 16:46
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DailyPushService {

    private final QweatherService qweatherService;
    private final OneWordService oneWordService;
    private final TianService tianService;

    /**
     * 获取日常推送数据
     *
     * @param day          恋爱日
     * @param girlBirthday 女生生日
     * @param boyBirthday  男生生日
     * @return
     */
    public DailyPush getDailyPushData(String day, String girlBirthday, String boyBirthday) {
        // 使用的默认配置
        QweatherRequest qweatherRequest = new QweatherRequest();

        // 城市数据
        QweatherCityInfoRes cityInfo = qweatherService.getCityInfo(qweatherRequest);
        QweatherCityInfoRes.Location location = Optional.of(cityInfo.getLocation()).orElse(new ArrayList<>()).get(0);
        String city = ObjectUtil.isNull(location) ? "" : location.getName();

        // 实时气温
        QweatherRealTimeWeatherRes realTimeWeather = qweatherService.getRealTimeWeather(qweatherRequest);
        QweatherRealTimeWeatherRes.Now now = Optional.of(realTimeWeather.getNow()).orElse(null);
        String weather = ObjectUtil.isNull(now) ? "" : now.getText() + "   " + now.getTemp() + "℃ (温度)   " + now.getFeelsLike() + "℃（体感）";
        String windScale = ObjectUtil.isNull(now) ? "" : now.getWindScale();

        // 逐天（未来3天）
        QweatherDayWeatherRes dayWeather3 = qweatherService.getDayWeather3(qweatherRequest);
        String tempMin = "";
        String tempMax = "";
        if (StrUtil.equals(dayWeather3.getCode(), "200")) {
            List<QweatherDayWeatherRes.Daily> dailies = Optional.of(dayWeather3.getDaily()).orElse(new ArrayList<>());
            tempMin = CollUtil.isEmpty(dailies) ? "" : dailies.get(0).getTempMin();
            tempMax = CollUtil.isEmpty(dailies) ? "" : dailies.get(0).getTempMax();
        } else {
            log.error("获取逐天天气（未来3天）失败，错误代码：" + dayWeather3.getCode());
        }

        // 天气指数
        QweatherLifeIndexRes lifeIndex = qweatherService.getLifeIndex(qweatherRequest);
        String sport = "";
        String dress = "";
        String sunscreen = "";
        String dressColor = DEFAULT_COLOR;
        String sunscreenColor = DEFAULT_COLOR;
        if (StrUtil.equals(lifeIndex.getCode(), "200")) {
            List<QweatherLifeIndexRes.Daily> dailies = Optional.of(lifeIndex.getDaily()).orElse(new ArrayList<>());
            if (CollUtil.isNotEmpty(dailies)) {
                for (QweatherLifeIndexRes.Daily daily : dailies) {
                    switch (daily.getType()) {
                        case "1":
                            // 运动指数
                            sport = "[" + daily.getCategory() + "]  " + daily.getText();
                            break;
                        case "3":
                            // 穿衣指数
                            dress = "[" + daily.getCategory() + "]  " + daily.getText();
                            if (NumberUtil.compare(Integer.parseInt(daily.getLevel()), 6) >= 0) {
                                dressColor = WARING_COLOR;
                            } else if (NumberUtil.compare(Integer.parseInt(daily.getLevel()), 2) <= 0) {
                                dressColor = COLD_COLOR;
                            }
                            break;
                        case "16":
                            // 防晒指数
                            sunscreen = "[" + daily.getCategory() + "]  " + daily.getText();
                            if (NumberUtil.compare(Integer.parseInt(daily.getLevel()), 4) >= 0) {
                                sunscreenColor = WARING_COLOR;
                            }
                            break;
                        default:
                    }
                }
            }
        } else {
            log.error("获取天气指数失败, 错误代码：" + lifeIndex.getCode());
        }

        // 恋爱第几天
        String loveDay = StrUtil.isBlank(day) ? "" : DateUtil.formatBetween(DateUtil.parseDate(day), DateUtil.date(), BetweenFormatter.Level.DAY);

        // 男、女方生日
        String girlDayMessage = "";
        String boyDayMessage = "";
        if (StrUtil.isNotBlank(girlBirthday)) {
            long girlDay = LoveUtil.getBetweenBirthday(girlBirthday);
            girlDayMessage = LoveUtil.getBetweenBirthdayMeg(girlDay, "她");
        }
        if (StrUtil.isNotBlank(boyBirthday)) {
            long boyDay = LoveUtil.getBetweenBirthday(boyBirthday);
            boyDayMessage = LoveUtil.getBetweenBirthdayMeg(boyDay, "他");
        }

        // 一言
        OneWordRes oneWordRes = oneWordService.getOneWord(new OneWordReq());
        String oneWord = "";
        if (ObjectUtil.isNotNull(oneWordRes) && StrUtil.isNotBlank(oneWordRes.getHitokoto())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(oneWordRes.getHitokoto());
            if (StrUtil.isNotBlank(oneWordRes.getFrom())) {
                stringBuilder.append("——");
                stringBuilder.append(oneWordRes.getFrom());
            }
            if (StrUtil.isNotBlank(oneWordRes.getFromWho())) {
                if (StrUtil.isBlank(oneWordRes.getFrom())) {
                    stringBuilder.append("——");
                }
                stringBuilder.append(oneWordRes.getFromWho());
            }
            oneWord = stringBuilder.toString();
        }

        // 彩虹屁
        TianRes tianRes = tianService.getRainbowFart();
        String rainbowFart = "";
        if (ObjectUtil.isNotNull(tianRes) && CollUtil.isNotEmpty(tianRes.getNewslist())) {
            rainbowFart = tianRes.getNewslist().get(0).getContent();
        } else {
            log.error("获取彩虹屁失败，错误代码：" + tianRes.getCode());
            log.error("获取彩虹屁失败，错误信息：" + tianRes.getMsg());

            // 舔狗
            TianRes lickingDog = tianService.getLickingDog();
            if (ObjectUtil.isNotNull(lickingDog) && CollUtil.isNotEmpty(lickingDog.getNewslist())) {
                rainbowFart = lickingDog.getNewslist().get(0).getContent();
            }
        }

        DailyPush dailyPush = new DailyPush();
        dailyPush.setDate(DateUtil.today() + " " + DateUtil.thisDayOfWeekEnum().toChinese());
        dailyPush.setCity(city);
        dailyPush.setWeather(weather);
        dailyPush.setTempMin(tempMin);
        dailyPush.setTempMax(tempMax);
        dailyPush.setSport(sport);
        dailyPush.setDress(dress);
        dailyPush.setDressColor(dressColor);
        dailyPush.setSunscreen(sunscreen);
        dailyPush.setSunscreenColor(sunscreenColor);
        dailyPush.setLoveDay(loveDay);
        dailyPush.setGirlBirthday(girlDayMessage);
        dailyPush.setBoyBirthday(boyDayMessage);
        dailyPush.setOneWord(oneWord);
        dailyPush.setRainbowFart(rainbowFart);
        return dailyPush;
    }

    /**
     * 获取推送模板
     */
    public String getLoveMsgTemp() {
        return "📅 {date} \n" +
                "城市: {city} \n" +
                "天气: {weather} \n" +
                "最低气温: {tempMin} \n" +
                "最高气温: {tempMax} \n" +
                "穿衣指数: {dress} \n" +
                "防晒指数: {sunscreen} \n" +
                "\n" +
                "今天是恋爱的第 {loveDay} ❤️ \n" +
                "{girlBirthday} \n" +
                "{boyBirthday} \n" +
                "\n" +
                "🌈: {rainbowFart}";
    }


}
