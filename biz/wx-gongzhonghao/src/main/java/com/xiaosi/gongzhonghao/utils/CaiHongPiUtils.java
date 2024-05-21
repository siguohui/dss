package com.xiaosi.gongzhonghao.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author cVzhanshi
 * @create 2022-08-04 22:58
 */
public class CaiHongPiUtils {


    public static String getCaiHongPi(String key) {
        String httpUrl = "http://api.tianapi.com/everyday/index?key=" + key;
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray newslist = jsonObject.getJSONArray("newslist");
        String content = newslist.getJSONObject(0).getString("note");
        return content;
    }

//    public static Map<String,String> getEnsentence() {
//        String httpUrl = "http://api.tianapi.com/zaoan/index?key=43495760e2990b2e23fbdc521902dea9";
//        BufferedReader reader = null;
//        Str
//        \ing result = null;
//        StringBuffer sbf = new StringBuffer();
//        try {
//            URL url = new URL(httpUrl);
//            HttpURLConnection connection = (HttpURLConnection) url
//                    .openConnection();
//            connection.setRequestMethod("GET");
//            InputStream is = connection.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            String strRead = null;
//            while ((strRead = reader.readLine()) != null) {
//                sbf.append(strRead);
//                sbf.append("\r\n");
//            }
//            reader.close();
//            result = sbf.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        JSONArray newslist = jsonObject.getJSONArray("newslist");
//        String en = newslist.getJSONObject(0).getString("en");
//        String zh = newslist.getJSONObject(0).getString("zh");
//        Map<String, String> map = new HashMap<>();
//        map.put("zh",zh);
//        map.put("en",en);
//        return map;
//    }
}
