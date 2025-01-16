package com.xiaosi.lock.service;

import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.lock.executor.RedisTemplateLockExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private LockTemplate lockTemplate;

    @Lock4j(keys = {"#userId"}, expire = 5000, acquireTimeout = 3000)
    public void processOrder(String userId) throws InterruptedException {
        // 模拟订单处理逻辑
        Thread.sleep(15000);
        System.out.println("Processing order for user: " + userId);
    }

    public void programmaticLock(String userId) {
        // 各种查询操作 不上锁        // ...        // 获取锁
//      final LockInfo lockInfo = lockTemplate.lock(userId, 30000L, 5000L, RedissonLockExecutor.class);
        final LockInfo lockInfo = lockTemplate.lock(userId, 30000L, 5000L, RedisTemplateLockExecutor.class);
        if (null == lockInfo) {
            throw new RuntimeException("业务处理中,请稍后再试");
        }
        // 获取锁成功，处理业务
        try {
            System.out.println("Processing order for user: " + userId);
        } finally {
            //释放锁
            lockTemplate.releaseLock(lockInfo);
        }        //结束
    }

}
