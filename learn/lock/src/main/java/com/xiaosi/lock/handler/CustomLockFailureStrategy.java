package com.xiaosi.lock.handler;

import com.baomidou.lock.LockFailureStrategy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CustomLockFailureStrategy implements LockFailureStrategy {
    @Override
    public void onLockFailure(String key, Method method, Object[] arguments) {
        // 处理锁获取失败的逻辑
        System.err.println("Failed to acquire lock for key: " + key + " in method: " + method.getName());
    }
}
