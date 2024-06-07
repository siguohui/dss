package com.xiaosi.lock.repeat;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class DuplicateRequestInterceptor {

    private static final String DUPLICATE_REQUEST_KEY_PATTERN = "duplicate_request:%s";

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public DuplicateRequestInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /*@Around("execution(* com.example.yourpackage.controller.YourController.yourMethod(..))")
    public Object preventDuplicateRequests(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求参数，作为唯一标识
        String requestHash = generateRequestHash(joinPoint.getArgs());

        long score = System.currentTimeMillis();

        // 添加请求到有序集合，如果已存在则返回0
        if (stringRedisTemplate.opsForZSet().add(DUPLICATE_REQUEST_KEY_PATTERN, requestHash, score) == 0) {
            throw new DuplicateRequestException("Duplicate request detected");
        }

        // 执行业务逻辑
        return joinPoint.proceed();
    }*/

    private String generateRequestHash(Object[] args) {
        // 根据实际需求生成请求哈希，可以考虑将所有重要参数组合成字符串并进行哈希
        StringBuilder hashBuilder = new StringBuilder();
        for (Object arg : args) {
            hashBuilder.append(arg.toString()).append(",");
        }
        return hashBuilder.toString().hashCode() + "";
    }
}

/*public class DuplicateRequestGatewayFilterFactory implements GatewayFilterFactory<DuplicateRequestGatewayFilterFactory.Config> {

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 获取请求参数，作为唯一标识
            String requestHash = generateRequestHash(request);

            // 检查请求是否已在数据库中
            boolean isDuplicate = checkDuplicateInDatabase(requestHash);

            if (isDuplicate) {
                throw new DuplicateRequestException("Duplicate request detected");
            }

            return chain.filter(exchange);
        };
    }

    private String generateRequestHash(ServerHttpRequest request) {
        // 实现根据请求头或请求体生成哈希的方法
    }

    private boolean checkDuplicateInDatabase(String requestHash) {
        // 在数据库中检查请求是否存在
    }

    public static class Config {}
}*/
