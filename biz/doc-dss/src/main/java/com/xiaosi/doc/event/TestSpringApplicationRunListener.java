package com.xiaosi.doc.event;

import com.xiaosi.doc.event.TestApplication;
import com.xiaosi.doc.event.TestApplicationEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @Description: TODO
 * @author: wengzx
 * @date: 2021年04月14日 16:01
 */
public class TestSpringApplicationRunListener implements SpringApplicationRunListener {

    private final SpringApplication application;

    private final String[] args;



    public TestSpringApplicationRunListener(SpringApplication application, String[] args1) {
        this.application = application;
        this.args = args1;
    }

    /*@Override
    public void starting() {
        System.out.println("TestSpringApplicationRunListener is starting");
    }*/

    /*@Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("TestSpringApplicationRunListener is environmentPrepared");
    }*/

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("TestSpringApplicationRunListener is contextPrepared");

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

        System.out.println("TestSpringApplicationRunListener is contextLoaded");
    }

//    @Override
//    public void started(ConfigurableApplicationContext context) {
//        System.out.println("TestSpringApplicationRunListener is started");
//        TestApplication testApplication = new TestApplication();
//        testApplication.setName("zhangsan");
//        testApplication.setAge(23);
//        TestApplicationEvent testApplicationEvent = new TestApplicationEvent(testApplication);
//        context.publishEvent(testApplicationEvent);
//    }

   /* @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("TestSpringApplicationRunListener is running");
    }*/

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("TestSpringApplicationRunListener is failed");
    }
}
