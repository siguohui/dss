package com.xiaosi.lock.service;

import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.lock.executor.RedisTemplateLockExecutor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaosi.lock.entity.Stu;
import com.xiaosi.lock.mapper.StuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {

    @Autowired
    private LockTemplate lockTemplate;
    @Autowired
    private StuService stuService;

//    @Lock4j(keys = {"#userId"}, expire = 60000, acquireTimeout = 1000)
    public void processOrder(String userId) throws InterruptedException {
        AtomicInteger num = new AtomicInteger(0);
        ExecutorService service = Executors.newFixedThreadPool(3, (r) -> new Thread(r, "玩家" + (num.getAndIncrement() + 1)));

        CountDownLatch latch = new CountDownLatch(3);
        for (int j = 0; j < latch.getCount(); j++) {
            service.submit(() -> {
                try {
                    stuService.addStu("xiaosi");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        service.shutdown();
    }

    public void programmaticLock(String userId) {
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

    public static void main(String[] args) {
        AtomicInteger num = new AtomicInteger(0);
        // 创建线程池
        ExecutorService service = Executors.newFixedThreadPool(10,(r) ->{
            return new Thread(r, "玩家" + (num.getAndIncrement() + 1));
        });
        try {
            CountDownLatch latch = new CountDownLatch(10);
            String[] all = new String[10];
            Random r = new Random();
            // j 代表10个玩家
            for (int j = 0; j < 10; j++) {
                int x = j;
                service.submit(() -> {
                    // 加载进度100
                    for (int i = 0; i <= 100; i++) {
                        try {
                            Thread.sleep(r.nextInt(100));
                        } catch (InterruptedException e) {
                        }
                        all[x] = Thread.currentThread().getName() + "(" + (i + "%") + ")";
                        // 这是一个回车字符。在文本终端中，它通常用于将光标移回当前行的开头。在某些情况下，它可能用于覆盖之前的内容，从而创建一种"刷新"或"更新"显示的效果。但请注意，它不会删除之前的内容，只是将光标移回行的开头。
                        System.out.print("\r" + Arrays.toString(all));
                    }
                    latch.countDown();
                });
            }
            // 主线程等所有玩家加载完成  然后进入游戏
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException("游戏加载异常", e);
        }
        System.out.print("\n游戏开始...");
        System.out.println("\n进入王者峡谷");
        service.shutdown();
    }

}
