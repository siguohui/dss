package com.xiaosi.wx.utils.RsaAes;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @module
 * @author: qingxu.liu
 * @date: 2023-02-08 16:41
 * @copyright  请求验证RSA & AES  统一验证切面
 **/
@Aspect
@Component
@Order(2)
@Slf4j
public class RequestRSAAspect {

    /**
     * 1> 获取请求参数
     * 2> 获取被请求接口的入参类型
     * 3> 判断是否为get请求 是则跳过AES解密判断
     * 4> 请求参数解密->封装到接口的入参
     */

    @Pointcut("execution(public * app.activity.controller.*.*(..))")
    public void requestRAS() {
    }

    @Around("requestRAS()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //=======AOP解密切面通知=======
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method methods = methodSignature.getMethod();
        RequestRSA annotation = methods.getAnnotation(RequestRSA.class);
        if (Objects.nonNull(annotation)){
            //获取请求的body参数
            Object data = getParameter(methods, joinPoint.getArgs());
            String body = JSONObject.toJSONString(data);
            //获取asy和sym的值
            JSONObject jsonObject = JSONObject.parseObject(body);
            String asy = jsonObject.get("asy").toString();
            String sym = jsonObject.get("sym").toString();
            //调用RequestDecryptionUtil方法解密，获取解密后的真实参数
            JSONObject decryption = RequestDecryptionUtil.getRequestDecryption(sym, asy);
            //获取接口入参的类
            String typeName = joinPoint.getArgs()[0].getClass().getTypeName();
            System.out.println("参数值类型："+ typeName);
            Class<?> aClass = joinPoint.getArgs()[0].getClass();
            //将获取解密后的真实参数，封装到接口入参的类中
            Object o = JSONObject.parseObject(decryption.toJSONString(), aClass);
            Object[] as = {o};
            return joinPoint.proceed(as);
        }
        return joinPoint.proceed();
    }

    /**
     * 根据方法和传入的参数获取请求参数 获取的是接口的入参
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}
