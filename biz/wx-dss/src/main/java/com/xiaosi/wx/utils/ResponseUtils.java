package com.xiaosi.wx.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.xiaosi.wx.exception.ServiceException;
import com.xiaosi.wx.pojo.JsonResult;
import com.xiaosi.wx.pojo.ResultEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public final class ResponseUtils {

    public static ObjectMapper objectMapper;  //final 构造函数赋值 如果final不能赋值

    public ResponseUtils(ObjectMapper objectMapper) {
        ResponseUtils.objectMapper = objectMapper;
    }

    public static void responseJson(HttpServletResponse response, JsonResult<?> jsonResult) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {

            response.getWriter().write(objectMapper.writeValueAsString(jsonResult));
        } catch (IOException e) {
            throw new ServiceException(ResultEnum.SERIAL_ERROR,"序列化失败： {} " + e.getMessage(), e);
        }
    }
}
