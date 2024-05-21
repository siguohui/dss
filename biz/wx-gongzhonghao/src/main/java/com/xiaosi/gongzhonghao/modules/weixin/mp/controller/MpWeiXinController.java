package com.xiaosi.gongzhonghao.modules.weixin.mp.controller;

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
import com.xiaosi.gongzhonghao.modules.weixin.mp.domain.TempMessage;
import com.xiaosi.gongzhonghao.modules.weixin.mp.service.MpWeiXinService;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-18 23:16
 */
@RestController
@RequestMapping("/sendToZh")
@RequiredArgsConstructor
public class MpWeiXinController {

    private final MpWeiXinService mpWeiXinService;

    @Autowired
    private QweatherService qweatherService;

    @Autowired
    private OneWordService oneWordService;

    @Autowired
    private TianService tianService;

    /**
     * 发送日常消息
     */
    @PostMapping("/sendDaily")
    public void sendDaily(@RequestBody TempMessage tempMessage) {
        mpWeiXinService.sendDaily(tempMessage.getUserOpenid(), tempMessage.getMessageTemplateId());
    }

    /**
     * 获取城市信息
     */
    @GetMapping("/getCityInfo")
    public QweatherCityInfoRes getCityInfo(@RequestParam("cityName") String cityName) {
        QweatherRequest qweatherRequest = new QweatherRequest();
        qweatherRequest.setLocation(cityName);
        return qweatherService.getCityInfo(qweatherRequest);
    }

    /**
     * 实时天气
     */
    @GetMapping("/getRealTimeWeather")
    public QweatherRealTimeWeatherRes getRealTimeWeather() {
        QweatherRequest qweatherRequest = new QweatherRequest();
        return qweatherService.getRealTimeWeather(qweatherRequest);
    }


    /**
     * 逐天天气预报（未来3天）
     */
    @GetMapping("/getDayWeather3")
    public QweatherDayWeatherRes getHourlyWeather24() {
        QweatherRequest qweatherRequest = new QweatherRequest();
        return qweatherService.getDayWeather3(qweatherRequest);
    }

    /**
     * 生活指数
     */
    @GetMapping("/getLifeIndex")
    public QweatherLifeIndexRes getLifeIndex() {
        QweatherRequest qweatherRequest = new QweatherRequest();
        qweatherRequest.setType("1,3,16");
        return qweatherService.getLifeIndex(qweatherRequest);
    }

    /**
     * 一言
     */
    @GetMapping("/getOneWord")
    public OneWordRes getOneWord(OneWordReq oneWordReq) {
        return oneWordService.getOneWord(oneWordReq);
    }

    /**
     * 彩虹屁
     */
    @GetMapping("/getRainbowFart")
    public TianRes getRainbowFart() {
        return tianService.getRainbowFart();
    }


    /**
     * 测试
     */
    @GetMapping("/test")
    public void test() throws IllegalAccessException {
        OneWordReq oneWordReq = new OneWordReq();
        oneWordReq.setC("123");
        oneWordReq.setEncode("123");
        oneWordReq.setCharset("asd");
        oneWordReq.setCallback("asd");
        oneWordReq.setSelect("sd");
        oneWordReq.setMin_length("f");
        oneWordReq.setMax_length("a");

        Field[] fields = OneWordReq.class.getDeclaredFields();
        List<Field> fieldList = Arrays.stream(fields).collect(Collectors.toList());
        for (Field field : fieldList) {
            System.out.println(field.getName());
            field.setAccessible(true);
            System.out.println(field.get(oneWordReq));
        }
    }

    /**
     * 发送日常消息
     */
    @PostMapping("/sendDailyDefault")
    public void sendDailyDefault() {
        mpWeiXinService.sendDailyDefault();
    }


}
