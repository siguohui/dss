package com.xiaosi.wx.support.advice;

import com.xiaosi.wx.annotation.IgnoreResult;
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

import java.lang.annotation.Annotation;

@RestControllerAdvice
@Slf4j
public class RestResponseBodyAdviceHandler implements ResponseBodyAdvice<Object> {
    private final String stringConverter="org.springframework.http.converter.StringHttpMessageConverter";
    /**
     * true:代表支持我们在响应前端的时候做一些处理(调用beforeBodyWrite方法)
     * false:不支持
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("supports:{}",returnType.getDeclaringClass().getName());
        Annotation[] annotations = returnType.getMethod().getAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation instanceof IgnoreResult){
                return false;
            }
        }
        /**
         * 排除swagger-ui请求返回数据增强
         */
        return !returnType.getDeclaringClass().getName().contains("springfox");
    }
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        /**
         * 当接口返回到类型消息转换器是StringHttpMessageConverter
         * 我们才需要把它转换成string
         */
        if(stringConverter.equalsIgnoreCase(selectedConverterType.getName())){
            HttpHeaders headers= response.getHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return ResponseUtils.objectMapper.writeValueAsString(JsonResult.success(body));
        }
        if(body instanceof JsonResult){
            return body;
        }
        return JsonResult.success(body);
    }
}
