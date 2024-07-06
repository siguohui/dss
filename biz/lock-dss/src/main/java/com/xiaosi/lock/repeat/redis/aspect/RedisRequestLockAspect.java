package com.xiaosi.lock.repeat.redis.aspect;

import java.lang.reflect.Method;
import com.xiaosi.lock.exception.ServiceException;
import com.xiaosi.lock.pojo.ResultEnum;
import com.xiaosi.lock.repeat.redis.key.CacheKeyGenerator;
import com.xiaosi.lock.repeat.redis.key.RequestKeyGenerator;
import com.xiaosi.lock.repeat.redis.annotation.RequestLock;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.StringUtils;

/**
 * @description 缓存实现
 * @author kyle0432
 * @date 2024/03/01 16:01
 */
@Aspect
@Configuration
@Order(2)
@RequiredArgsConstructor
public class RedisRequestLockAspect {

    /*@Bean
    public CacheKeyGenerator cacheKeyGenerator(){
        return new CacheKeyGeneratorImp();
    }*/

    private final StringRedisTemplate stringRedisTemplate;
    private final CacheKeyGenerator cacheKeyGenerator;

//    execution(* com.*.service..*.*(..))
//    execution(* com..service..*.*(..))
//    execution(* com.example.service..*.*(..))
//    execution(* *(..))
//    @Pointcut("execution(* com.xiaosi.lock.controller..*(..))")
    public void pt(){
    }

//    @Around("execution(* *..*.*(..))")
//    @Around("execution(public * * (..))")
//    @Around("execution(* *(..))")
    @Around("execution(public * * (..)) && @annotation(com.xiaosi.lock.repeat.redis.annotation.RequestLock)")
//    @Around("pt()")
    public Object interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RequestLock requestLock = method.getAnnotation(RequestLock.class);
        if (StringUtils.isEmpty(requestLock.prefix())) {
            throw new ServiceException(ResultEnum.PARAMETER_REPEART, "重复提交前缀不能为空");
        }
        //获取自定义key
        final String lockKey = cacheKeyGenerator.getLockKey(joinPoint);
        // 使用RedisCallback接口执行set命令，设置锁键；设置额外选项：过期时间和SET_IF_ABSENT选项
        final Boolean success = stringRedisTemplate.execute(
                (RedisCallback<Boolean>)connection -> connection.set(lockKey.getBytes(), new byte[0],
                        Expiration.from(requestLock.expire(), requestLock.timeUnit()),
                        RedisStringCommands.SetOption.SET_IF_ABSENT));
        if (!success) {
            throw new ServiceException(ResultEnum.BIZ_CHECK_FAIL, "您的操作太快了,请稍后重试");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new ServiceException(ResultEnum.BIZ_CHECK_FAIL, "系统异常");
        }
    }
}
