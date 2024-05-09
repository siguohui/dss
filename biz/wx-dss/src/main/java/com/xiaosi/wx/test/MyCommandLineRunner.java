package com.xiaosi.wx.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("CommandLineRunner已启动！");
    }

    /*CommandLineRunner 和 ApplicationRunner 的作用是相同的。不同之处在于 CommandLineRunner 接口的 run()
    方法接收 String 数组作为参数，即是最原始的参数，没有做任何处理；而 ApplicationRunner 接口的 run() 方法接收 ApplicationArguments 对象作为参数，
    是对原始参数做了进一步的封装。当程序启动时，我们传给 main() 方法的参数可以被实现 CommandLineRunner 和 ApplicationRunner 接口的类的 run() 方法访问，
    即可接收启动服务时传过来的参数。我们可以创建多个实现 CommandLineRunner 和 ApplicationRunner 接口的类。
    为了使他们按一定顺序执行，可以使用 @Order 注解或实现 Ordered 接口。*/

}
