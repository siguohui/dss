package com.xiaosi.wx.support.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.xiaosi.wx.annotation.IgnoreResult;
import com.xiaosi.wx.page.annotation.PageX;
import com.xiaosi.wx.pojo.JsonResult;
import com.xiaosi.wx.utils.ResponseUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseBodyAdviceHandler implements ResponseBodyAdvice<Object> {
    private final String stringConverter="org.springframework.http.converter.StringHttpMessageConverter";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("supports:{}",returnType.getDeclaringClass().getName());
        return !returnType.hasMethodAnnotation(IgnoreResult.class)
                && !returnType.getParameterType().isAssignableFrom(JsonResult.class)
                && !returnType.getDeclaringClass().getName().contains("springdoc");

    }
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if(returnType.hasMethodAnnotation(PageX.class) || ((body instanceof Page) && body instanceof List)){
            body = new PageInfo<>((List<?>) body);
        }

        if (returnType.getParameterType().isAssignableFrom(void.class)) {
            return JsonResult.success();
        }

        if (!(body instanceof JsonResult)) {
            if (body instanceof String  || stringConverter.equalsIgnoreCase(selectedConverterType.getName())) {
                try {
                    HttpHeaders headers= response.getHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    return ResponseUtils.objectMapper.writeValueAsString(JsonResult.success(body));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            return JsonResult.success(body);
        }
        return body;
    }
}
