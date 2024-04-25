package com.xiaosi.wx.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaosi.wx.pojo.BaseResponse;
import com.xiaosi.wx.pojo.JsonResult;
import com.xiaosi.wx.pojo.ResultEnum;
import com.xiaosi.wx.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    /**
     * 捕获基类Throwable
     * HttpStatus.INTERNAL_SERVER_ERROR 对应 code=500
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult handler(Throwable e) {
        log.error(e.getMessage(), e);
        return JsonResult.fail(ResultEnum.BUSY);
    }

    /**
     * 捕获系统中的自定义异常
     * BAD_REQUEST 对应 code=400
     * ServiceException是系统中自定义的异常类
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult<BaseResponse> handler(ServiceException e) {
        //处理异常
        log.warn(e.getMessage(), e);
        return JsonResult.fail(e.getMessage());
    }

    /**
     * 参数格式错误 @RequestParam 上 validate 失败抛出的异常
     * 对应的就是校验RequestParam参数和校验PathVariable参数，这两种校验不通过，系统会抛出此异常
     * ParameterErrorCodeEnum.PARAMETER_NOT_VALID模板信息为："参数异常：{}，请检查后重试"
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult<BaseResponse> handler(ConstraintViolationException e) {
        //处理异常
        ArrayNode arrayNode = ResponseUtils.objectMapper.createArrayNode();
        e.getConstraintViolations().forEach(constraintViolation -> {
            for (Path.Node next : constraintViolation.getPropertyPath()) {
                if (ElementKind.PARAMETER.equals(next.getKind())) {

                    String receivedValue = ObjectUtil.defaultIfNull(constraintViolation.getInvalidValue(), "null").toString();
                    ObjectNode objectNode = ResponseUtils.objectMapper.createObjectNode()
                            .put("参数", next.getName())
                            .put("接收到的值", receivedValue)
                            .put("错误信息", constraintViolation.getMessage());

                    arrayNode.add(objectNode);
                }
            }
        });
        String msg;
        if (arrayNode.size() > 0) {
            msg = arrayNode.toString();
        } else {
            msg = e.getMessage();
        }
        return JsonResult.fail(ResultEnum.PARAMETER_NOT_VALID.getCode(), msg);
    }

    /**
     * 参数格式错误 @RequestBody 上 validate 失败抛出的异常
     * 对应的是校验RequestBody入参，校验不通过，系统会抛出此异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult<BaseResponse> handler(MethodArgumentNotValidException e) {
        //处理异常
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        ArrayNode arrayNode = ResponseUtils.objectMapper.createArrayNode();
        String msg = null;

        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                String field = ((FieldError) error).getField();
                String defaultMessage = error.getDefaultMessage();
                Object rejectedValue = ((FieldError) error).getRejectedValue();
                String receivedValue = ObjectUtil.defaultIfNull(rejectedValue, "null").toString();
                ObjectNode objectNode = ResponseUtils.objectMapper.createObjectNode()
                        .put("参数", field)
                        .put("接收到的值", receivedValue)
                        .put("错误信息", defaultMessage);
                arrayNode.add(objectNode);
                msg = arrayNode.toString();
            } else {
                msg = error.getDefaultMessage();
            }
        }

        return JsonResult.fail(ResultEnum.PARAMETER_NOT_VALID.getCode(), msg);
    }

    /**
     * RequestParam 注解中 required = true 的情况拦截
     * springServlet层次的异常
     * MISSING_REQUEST_PARAMETER的模板是："缺少参数:[{}]，类型为[{}]"
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult<BaseResponse> handler(MissingServletRequestParameterException e) {
        //处理异常
        String parameterName = e.getParameterName();
        String parameterType = e.getParameterType();
        return JsonResult.fail(ResultEnum.MISSING_PARAMETER);
    }

    /**
     * 请求方法不支持 例如：GET/POST不对应
     * METHOD_NOT_SUPPORT的模板为"请求方法[{}]不支持，支持的方法为{}"
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public JsonResult handler(HttpRequestMethodNotSupportedException e) {
        //处理异常
        List<String> supportedMethods = Arrays.asList(e.getSupportedMethods());
        return JsonResult.fail(ResultEnum.METHOD_NOT_SUPPORT, supportedMethods);
    }

    /**
     * 接口不存在
     * NO_HANDLER_FOUND的模板是:"接口[{}]不存在"
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public JsonResult handler(NoHandlerFoundException e) {
        //处理异常
        String requestURL = e.getRequestURL();
        return JsonResult.fail(ResultEnum.NO_HANDLER_FOUND, requestURL);
    }


    //1、处理参数验证异常 MethodArgumentNotValidException
    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResult handleValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError firstFieldError = CollectionUtil.getFirst(bindingResult.getFieldErrors());
        String exceptionStr = Optional.ofNullable(firstFieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(StrUtil.EMPTY);
        log.error("[{}] {} [ex] {}", request.getMethod(),"URL:", exceptionStr);
        return JsonResult.fail(exceptionStr);
    }

    // 处理自定义异常:AbstractException
    @ExceptionHandler(value = {AbstractException.class})
    public JsonResult handleAbstractException(HttpServletRequest request, AbstractException ex) {
        String requestURL = "URL地址";
        log.error("[{}] {} [ex] {}", request.getMethod(), requestURL, ex.toString());
        return JsonResult.fail(ex.toString());
    }

    // 兜底处理：Throwable
    @ExceptionHandler(value = Throwable.class)
    public JsonResult handleThrowable(HttpServletRequest request, Throwable throwable) {
//        String requestURL = getUrl(request);
        log.error("[{}] {} ", request.getMethod(), "URL地址", throwable);
        return JsonResult.fail(ResultEnum.RESULT_FAIL.getMsg());
    }
}
