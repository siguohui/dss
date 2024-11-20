package org.xiaosi.stomp.work.oneword;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 一言
 * <a href="https://developer.hitokoto.cn/sentence/">开发文档</a>
 *
 * @author kcaco
 * @since 2022-08-19 12:13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OneWordService {
    private final OkHttpClient okHttpClient;


    /**
     * 一言获取
     *
     * @param oneWordReq 请求体,为null使用默认配置
     * @return 一言
     */
    public OneWordRes getOneWord(OneWordReq oneWordReq) {
        // 构建请求体
        RequestBody formBody = new FormBody.Builder().build();
        if (ObjectUtil.isNotNull(oneWordReq)) {
            Field[] fields = OneWordReq.class.getDeclaredFields();
            List<Field> fieldList = Arrays.stream(fields).collect(Collectors.toList());
            FormBody.Builder builder = new FormBody.Builder();
            for (Field field : fieldList) {
                String fieldName = field.getName();
                field.setAccessible(true);
                try {
                    String fieldValue = (String) field.get(oneWordReq);
                    if (StrUtil.isBlank(fieldValue)) {
                        break;
                    }
                    builder.add(fieldName, fieldValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            formBody = builder.build();
        }

        Request request = new Request.Builder()
                .url(OneWordApiConstant.API)
                .post(formBody)
                .build();

        OneWordRes resData = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (ObjectUtil.isNull(response.body())) {
                throw new RuntimeException("没有返回体！");
            }

            resData = new Gson().fromJson(response.body().string(), OneWordRes.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return resData;
    }

}
