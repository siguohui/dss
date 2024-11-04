package com.xiaosi.xxljob.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class JobHandler {

    @XxlJob("dailyEquipData")
     public void test(){
//         throw new RuntimeException("111111111111111");
        System.out.println("1111111111111111");
     }
}
