package com.xiaosi.doc;

import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MapTest {


//    @SneakyThrows
    public static void main(String[] args){
        //多线程测试:使用多线程向 list 集合写入8000个数据
        List<String> list = new ArrayList<>();
        Map<String, List<String>> map = new HashMap(2,0.75f);// 创建线程池
        Executor executor = Executors.newFixedThreadPool(8);
        // 多线程写入
        CountDownLatch latch = new CountDownLatch(8);
        for(int i = 0; i<8; i++) {
            executor.execute(() -> {
                List<String> list1 = new ArrayList<>();
                for (int j = 0; j < 1000; j++) {
                    list1.add("aa");
                }
                map.put(IdUtil.getSnowflakeNextIdStr(), list1);
                latch.countDown();
            });
        }
        //等待全部线程执行完毕
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
        }
        // 写入 hlist 集合
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
        }
        map.forEach((key,value)-> list.addAll(value));
        System.out.println(list.size());//集合大小小于8000
    }

}
