
https://www.cnblogs.com/linlf03/p/12384829.html
https://www.jianshu.com/p/a43e532d2b4d
通过堆栈分析，我们可以知道，Aware接口 的调用点有两处

a.  AbstractCapableBeanFactory工厂类 的 initializeBean方法 里的 invokeAwareMethods(beanName, bean)方法
b. ApplicationContextAwareProcessor后处理器 的 postProcessBeforeInitialization方法里 invokeAwareInterfaces(bean)方法

第一个调用点是 AbstractCapableBeanFactory工厂类 的 initializeBean方法 里的 invokeAwareMethods(beanName, bean)方法，下面我们对该方法进行分析
该方法处理 Aware接口 的顺序：BeanNameAware -> BeanClassLoaderAware -> BeanFactoryAware

第二个调用点是 ApplicationContextAwareProcessor后处理器 的 postProcessBeforeInitialization方法 里的 invokeAwareInterfaces(bean)方法，下面我们来具体分析不同类型的Aware具体加载了什么
该方法处理 Aware接口 的顺序：EnvironmentAware -> EmbeddedValueResolverAware -> ResourceLoaderAware ->ApplicationEventPublisherAware -> MessageSourceAware -> ApplicationContextAware


ApplicationContextAwareProcessor实现了BeanPostProcessor接口，
我们知道该接口可以在Bean的初始化前后去做一些操作。那么我们看看ApplicationContextAwareProcessor实现的方法中的操作


String message = messageSource.getMessage("key", null, Locale.getDefault());

Resource resource = resourceLoader.getResource("classpath:config.xml");

String contextPath = servletContext.getContextPath();

/ 获取当前环境
String profile = environment.getActiveProfiles()[0];
// 获取配置文件中的属性值
String value = environment.getProperty("key");

ServletConfigAware
ApplicationContextInitializer



public class MyBean implements EmbeddedValueResolverAware {
private EmbeddedValueResolver embeddedValueResolver;
    @Override
    public void setEmbeddedValueResolver(EmbeddedValueResolver embeddedValueResolver) {
        this.embeddedValueResolver = embeddedValueResolver;
    }
    public void doSomething() {
        // 获取属性值
        String value = embeddedValueResolver.resolveStringValue("${key}");
        // ...
    }
}


public class MyBean implements LoadTimeWeaverAware {
private LoadTimeWeaver loadTimeWeaver;
    @Override
    public void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver) {
        this.loadTimeWeaver = loadTimeWeaver;
    }
    public void doSomething() {
        // 动态加载类
        ClassLoader classLoader = loadTimeWeaver.getClassLoader();
        Class&lt;?&gt; clazz = classLoader.loadClass("com.example.OtherClass");
        // ...
    }
}



public class MyBean implements ApplicationEventPublisherAware {
private ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    public void doSomething() {
        // 发布事件
        applicationEventPublisher.publishEvent(new MyEvent(this, "event data"));
        // ...
    }
}

@Component
public class MyEventListener implements ApplicationListener&lt;MyEvent&gt; {
@Override
public void onApplicationEvent(MyEvent event) {
// 处理事件
// ...
}
}



public class MyBean implements ConversionServiceAware {
private ConversionService conversionService;
    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
    public void doSomething() {
        // 类型转换
        String str = "123";
        Integer value = conversionService.convert(str, Integer.class);
        // ...
    }
}

ConfigurableApplicationContext
prepareBeanFactory(beanFactory);
@Component
public class ResultCommandLineRunner implements CommandLineRunner, EnvironmentAware , MyAware{
