package com.xiaosi.lock.controller;

import com.xiaosi.lock.pojo.JsonResult;
import com.xiaosi.lock.repeat.redis.annotation.RequestLock;
import com.xiaosi.lock.repeat.redis.param.AddReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private RedisLockRegistry redisLockRegistry;
    @GetMapping("test")
    public void test() throws InterruptedException {
        Lock lock = redisLockRegistry.obtain("lock");
        boolean b1 = lock.tryLock(3, TimeUnit.SECONDS);
        log.info("b1 is : {}", b1);

        TimeUnit.SECONDS.sleep(5);

        boolean b2 = lock.tryLock(3, TimeUnit.SECONDS);
        log.info("b2 is : {}", b2);

        lock.unlock();
        lock.unlock();
    }


    @PostMapping("/add")
//    @RequiresPermissions(value = "add")
//    @Log(methodDesc = "添加用户")
    @RequestLock
    public JsonResult<AddReq> add(@RequestBody AddReq addReq) {
        return JsonResult.success(addReq);
    }
}
