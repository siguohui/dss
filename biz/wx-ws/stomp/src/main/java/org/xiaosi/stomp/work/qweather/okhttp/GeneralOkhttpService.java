package org.xiaosi.stomp.work.qweather.okhttp;

import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-19 13:27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeneralOkhttpService {

    private final OkHttpClient okHttpClient;

    /**
     * 通用获取数据方法
     *
     * @param url      请求地址
     * @param resClazz 响应体Class
     * @param errMsg   错误信息
     * @param <T>      泛型
     * @return T 响应体
     */
    public <T> T generalGetMethod(String url, Class<T> resClazz, String errMsg) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        T resData = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (ObjectUtil.isNull(response.body())) {
                throw new RuntimeException(errMsg);
            }
            String string = response.body().string();
            System.out.println(string);
            resData = new Gson().fromJson(string, resClazz);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return resData;
    }

}
