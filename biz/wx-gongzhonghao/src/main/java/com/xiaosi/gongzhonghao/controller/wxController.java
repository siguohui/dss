package com.xiaosi.gongzhonghao.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaosi.gongzhonghao.utils.CaiHongPiUtils;
import com.xiaosi.gongzhonghao.utils.DateUtil;
import com.xiaosi.gongzhonghao.utils.HttpUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/wx")
public class wxController {

    @Value("${wx.config.appId}")
    private String appId;
    @Value("${wx.config.appSecret}")
    private String appSecret;
    @Value("${wx.config.templateId}")
    private String templateId;
    @Value("${wx.config.openid}")
    private String openid;
    @Value("${weather.config.appid_free}")
    private String weatherAppId;
    @Value("${weather.config.appsecret_free}")
    private String weatherAppSecret;
    @Value("${message.config.togetherDate}")
    private String togetherDate;
    @Value("${message.config.birthday}")
    private String birthday;
    // @Value("${message.config.message}")
    @Value("${weather.config.cityid}")
    private String cityid;

    @Value("${caihongpi.config.key}")
    private String key;

    private String message;

    private String accessToken = "";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 获取Token
     * 每天早上7：00执行推送
     *
     * @return
     */
    @Scheduled(cron = "0 00 7 ? * ? ")
    @RequestMapping("/getAccessToken")
    public String getAccessToken() {
        //这里直接写死就可以，不用改，用法可以去看api
        String grant_type = "client_credential";
        //封装请求数据
        String params = "grant_type=" + grant_type + "&secret=" + appSecret + "&appid=" + appId;
        //发送GET请求
        String sendGet = HttpUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        // 解析相应内容（转换成json对象）
        JSONObject jsonObject1 = JSONObject.parseObject(sendGet);
        logger.info("微信token响应结果=" + jsonObject1);
        //拿到accesstoken
        accessToken = (String) jsonObject1.get("access_token");
        return sendWeChatMsg(accessToken);
    }

    @Scheduled(cron = "0 00 23 ? * ? ")
    @RequestMapping("/getAccessToken2")
    public String getAccessToken2() {
        //这里直接写死就可以，不用改，用法可以去看api
        String grant_type = "client_credential";
        //封装请求数据
        String params = "grant_type=" + grant_type + "&secret=" + appSecret + "&appid=" + appId;
        //发送GET请求
        String sendGet = HttpUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        // 解析相应内容（转换成json对象）
        JSONObject jsonObject1 = JSONObject.parseObject(sendGet);
        logger.info("微信token响应结果=" + jsonObject1);
        //拿到accesstoken
        accessToken = (String) jsonObject1.get("access_token");
        return sendWeChatMsg2(accessToken);
    }

    /**
     * 发送微信消息
     *
     * @return
     */
    public String sendWeChatMsg(String accessToken) {

        String[] openIds = openid.split(",");
        List<JSONObject> errorList = new ArrayList();
        for (String openId : openIds) {
            JSONObject templateMsg = new JSONObject(new LinkedHashMap<>());

            templateMsg.put("touser", openId);
            templateMsg.put("template_id", templateId);


            JSONObject first = new JSONObject();
            String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
            String week = DateUtil.getWeekOfDate(new Date());
            String day = date + " " + week;
            first.put("value", day);
            first.put("color", "#EED016");

            // not free url
            // String TemperatureUrl = "https://www.yiketianqi.com/free/day?appid=" + weatherAppId + "&appsecret=" + weatherAppSecret + "&unescape=1";
            // free url
            String TemperatureUrl = "https://v0.yiketianqi.com/api?unescape=1&version=v91&appid=86863212+&appsecret=d6utdbDO&cityid=" + cityid;
            String sendGet = HttpUtil.sendGet(TemperatureUrl, null);
            JSONObject temperature = JSONObject.parseObject(sendGet);
            JSONArray dataArr = new JSONArray();
            String address = "无法识别"; //城市
            String temHigh = "无法识别"; //最高温度
            String temLow = "无法识别"; //最低温度
            String weatherStatus = "";//天气状态
            String promptMsg = "";
            String narrative = "";
            if (temperature.getString("city") != null) {

                dataArr = temperature.getJSONArray("data");
                temHigh = dataArr.getJSONObject(0).getString("tem1") + "°";
                if (Integer.parseInt(dataArr.getJSONObject(0).getString("tem1")) > 30) {
                    promptMsg = "今日温度有点高哦，记住防晒。";
                }
                temLow = dataArr.getJSONObject(0).getString("tem2") + "°";
                if (Integer.parseInt(dataArr.getJSONObject(0).getString("tem2")) < 10) {
                    promptMsg = "今日温度有点低，小马记得多加衣。";
                }
                address = temperature.getString("city");
                weatherStatus = dataArr.getJSONObject(0).getString("wea");
                narrative = dataArr.getJSONObject(0).getString("narrative");
            }

            JSONObject city = new JSONObject();
            city.put("value", address);
            city.put("color", "#60AEF2");

            //String weather = weatherStatus + ", 温度:" + temLow + " ~ " + temHigh + "," + promptMsg + "," + narrative;
            String weather = weatherStatus + ", 温度:" + temLow + " ~ " + temHigh;

            JSONObject promptMsgObj = new JSONObject();
            promptMsgObj.put("value", promptMsg);
            promptMsgObj.put("color", "#60AEF2");

            JSONObject narrativeObj = new JSONObject();
            narrativeObj.put("value", narrative);
            narrativeObj.put("color", "#60AEF2");

            JSONObject temperatures = new JSONObject();

            temperatures.put("value", weather);
            temperatures.put("color", "#44B549");

            JSONObject birthDate = new JSONObject();
            String birthDay = "无法识别";
            try {
                Calendar calendar = Calendar.getInstance();
                String newD = calendar.get(Calendar.YEAR) + "-" + birthday;
                birthDay = DateUtil.daysBetween(date, newD);
                if (Integer.parseInt(birthDay) < 0) {
                    Integer newBirthDay = Integer.parseInt(birthDay) + 365;
                    //birthDay = newBirthDay + "天";
                    birthDay = newBirthDay + "";
                } else {
                    //birthDay = birthDay + "天";
                    birthDay = birthDay;
                }
            } catch (ParseException e) {
                logger.error("togetherDate获取失败" + e.getMessage());
            }
            birthDate.put("value", "  " + birthDay + "  ");
            birthDate.put("color", "#6EEDE2");


            JSONObject togetherDateObj = new JSONObject();
            String togetherDay = "";
            try {
                togetherDay = DateUtil.daysBetween(togetherDate, date) + " 天,小马要永远开心o(*￣▽￣*)ブ";
            } catch (ParseException e) {
                logger.error("togetherDate获取失败" + e.getMessage());
            }
            togetherDateObj.put("value", " " + togetherDay + " ");
            togetherDateObj.put("color", "#FEABB5");

            JSONObject messageObj = new JSONObject();

            message = CaiHongPiUtils.getCaiHongPi(key);
            messageObj.put("value", message + " ");
            messageObj.put("color", "#C79AD0");


            JSONObject data = new JSONObject(new LinkedHashMap<>());
            data.put("first", first);
            data.put("city", city);
            data.put("temperature", temperatures);
            data.put("togetherDate", togetherDateObj);
            data.put("birthDate", birthDate);
            data.put("message", messageObj);
            data.put("promptMsg", promptMsgObj);
            data.put("narrative", narrativeObj);

            templateMsg.put("data", data);
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

            String sendPost = HttpUtil.sendPost(url, templateMsg.toJSONString());
            JSONObject WeChatMsgResult = JSONObject.parseObject(sendPost);
            if (!"0".equals(WeChatMsgResult.getString("errcode"))) {
                JSONObject error = new JSONObject();
                error.put("openid", openId);
                error.put("errorMessage", WeChatMsgResult.getString("errmsg"));
                errorList.add(error);
            }
            logger.info("sendPost=" + sendPost);
        }
        JSONObject result = new JSONObject();
        result.put("result", "success");
        result.put("errorData", errorList);
        return result.toJSONString();

    }

    public String sendWeChatMsg2(String accessToken) {

        String[] openIds = openid.split(",");
        List<JSONObject> errorList = new ArrayList();
        for (String openId : openIds) {
            JSONObject templateMsg = new JSONObject(new LinkedHashMap<>());

            templateMsg.put("touser", openId);
            templateMsg.put("template_id", "40YZ5xFYzijbO9rqwVA2aKZ7VoieaSss1ZWIERmPdfo");


            JSONObject first = new JSONObject();
            String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
            String week = DateUtil.getWeekOfDate(new Date());
            String day = date + " " + week;
            first.put("value", day);
            first.put("color", "#EED016");

            // not free url
            // String TemperatureUrl = "https://www.yiketianqi.com/free/day?appid=" + weatherAppId + "&appsecret=" + weatherAppSecret + "&unescape=1";
            // free url
            String TemperatureUrl = "https://v0.yiketianqi.com/api?unescape=1&version=v91&appid=86863212+&appsecret=d6utdbDO&cityid=" + cityid;
            String sendGet = HttpUtil.sendGet(TemperatureUrl, null);
            JSONObject temperature = JSONObject.parseObject(sendGet);
            JSONArray dataArr = new JSONArray();
            String address = "无法识别"; //城市
            String temHigh = "无法识别"; //最高温度
            String temLow = "无法识别"; //最低温度
            String weatherStatus = "";//天气状态
            String promptMsg = "";
            String narrative = "";
            if (temperature.getString("city") != null) {

                dataArr = temperature.getJSONArray("data");
                temHigh = dataArr.getJSONObject(1).getString("tem1") + "°";
                if (Integer.parseInt(dataArr.getJSONObject(1).getString("tem1")) > 30) {
                    promptMsg = "明日温度有点高哦，记住防晒。";
                }
                temLow = dataArr.getJSONObject(1).getString("tem2") + "°";
                if (Integer.parseInt(dataArr.getJSONObject(1).getString("tem2")) < 10) {
                    promptMsg = "明日温度有点低，小马记得多加衣。";
                }
                address = temperature.getString("city");
                weatherStatus = dataArr.getJSONObject(1).getString("wea");
                narrative = dataArr.getJSONObject(1).getString("narrative");
            }

            JSONObject city = new JSONObject();
            city.put("value", address);
            city.put("color", "#60AEF2");

            //String weather = weatherStatus + ", 温度:" + temLow + " ~ " + temHigh + "," + promptMsg + "," + narrative;
            String weather = weatherStatus + ", 温度:" + temLow + " ~ " + temHigh+"，";

            JSONObject promptMsgObj = new JSONObject();
            promptMsgObj.put("value", promptMsg);
            promptMsgObj.put("color", "#60AEF2");

            JSONObject narrativeObj = new JSONObject();
            narrativeObj.put("value", narrative);
            narrativeObj.put("color", "#60AEF2");

            JSONObject temperatures = new JSONObject();
            temperatures.put("value", weather);
            temperatures.put("color", "#44B549");

            JSONObject birthDate = new JSONObject();
            String birthDay = "无法识别";
            try {
                Calendar calendar = Calendar.getInstance();
                String newD = calendar.get(Calendar.YEAR) + "-" + birthday;
                birthDay = DateUtil.daysBetween(date, newD);
                if (Integer.parseInt(birthDay) < 0) {
                    Integer newBirthDay = Integer.parseInt(birthDay) + 365;
                    //birthDay = newBirthDay + "天";
                    birthDay = newBirthDay + "";
                } else {
                    //birthDay = birthDay + "天";
                    birthDay = birthDay;
                }
            } catch (ParseException e) {
                logger.error("togetherDate获取失败" + e.getMessage());
            }
            birthDate.put("value", "  " + birthDay + "  ");
            birthDate.put("color", "#6EEDE2");


            JSONObject togetherDateObj = new JSONObject();
            String togetherDay = "";
            try {
                togetherDay = DateUtil.daysBetween(togetherDate, date) + " 天,小马要永远开心o(*￣▽￣*)ブ";
            } catch (ParseException e) {
                logger.error("togetherDate获取失败" + e.getMessage());
            }
            togetherDateObj.put("value", " " + togetherDay + " ");
            togetherDateObj.put("color", "#FEABB5");

            JSONObject messageObj = new JSONObject();

            message = CaiHongPiUtils.getCaiHongPi(key);
            messageObj.put("value", message + " ");
            messageObj.put("color", "#C79AD0");


            JSONObject data = new JSONObject(new LinkedHashMap<>());
            data.put("first", first);
            data.put("city", city);
            data.put("temperature", temperatures);
            data.put("togetherDate", togetherDateObj);
            data.put("birthDate", birthDate);
            data.put("message", messageObj);
            //data.put("promptMsg", promptMsgObj);
            //data.put("narrative", narrativeObj);


            templateMsg.put("data", data);
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

            String sendPost = HttpUtil.sendPost(url, templateMsg.toJSONString());
            JSONObject WeChatMsgResult = JSONObject.parseObject(sendPost);
            if (!"0".equals(WeChatMsgResult.getString("errcode"))) {
                JSONObject error = new JSONObject();
                error.put("openid", openId);
                error.put("errorMessage", WeChatMsgResult.getString("errmsg"));
                errorList.add(error);
            }
            logger.info("sendPost=" + sendPost);
        }
        JSONObject result = new JSONObject();
        result.put("result", "success");
        result.put("errorData", errorList);
        return result.toJSONString();

    }

}
