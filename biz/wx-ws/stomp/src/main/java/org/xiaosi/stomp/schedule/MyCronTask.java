package org.xiaosi.stomp.schedule;

import cn.hutool.core.util.StrUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;
import org.xiaosi.stomp.utils.RedisUtil;
import org.xiaosi.stomp.work.config.LoveConfig;
import org.xiaosi.stomp.work.domain.DailyPush;
import org.xiaosi.stomp.work.service.DailyPushService;
import org.xiaosi.stomp.work.service.WorkWeiXinService;
import org.xiaosi.stomp.work.util.KcacoUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Slf4j
@EnableScheduling
public class MyCronTask {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private WorkWeiXinService workWeiXinService;
    @Autowired
    private LoveConfig loveConfig;
    @Autowired
    private DailyPushService dailyPushService;

    @Scheduled(cron = "0/10 * * * * *")
    void cronSchedule(){
        String sz = getSZ();
        List<String> split = StrUtil.split(sz, " ");
//        String sz = getShSz();
        String szzs = split.get(1);
        BigDecimal decimal = new BigDecimal(szzs);
//        if(decimal.compareTo(new BigDecimal("3360")) > -1){
//            pushMsgWx(szzs);
//        }
        String fromUser="oKIee6K9bDb7hT-Q8SNnAXnx3rfo";
        String touser="gh_38e9b4eba3d2";
        redisUtil.set("sz",decimal);
        log.info(sz);
        simpMessagingTemplate.convertAndSend("/topic/sz",sz);
    }

    public void pushMsgWx(String szzs) {
        // 获取消息模板
        String messageTemplate = getLoveMsgTemp();

        // 获取推送数据
//        DailyPush dailyPush = dailyPushService.getDailyPushData(loveConfig.getDay(), loveConfig.getGirlBirthday(), loveConfig.getBoyBirthday());

        DailyPush dailyPush = new DailyPush();
        dailyPush.setSzzs(szzs);
        // 填充消息模板
        Map<String, String> objectFieldValueMap = KcacoUtil.getObjectFieldValueMap(DailyPush.class, dailyPush);
        String message = StrUtil.format(messageTemplate, objectFieldValueMap);
        // 发送消息
        workWeiXinService.sendTextMessage(message);
    }


    /**
     * 获取推送模板
     */
    public String getLoveMsgTemp() {
        return "上证指数: {szzs} \n"+
                "📅 {date} \n" +
                "城市: {city} \n" +
                "天气: {weather} \n" +
                "最低气温: {tempMin} \n" +
                "最高气温: {tempMax} \n" +
                "穿衣指数: {dress} \n" +
                "防晒指数: {sunscreen} \n" +
                "\n" +
                "今天是恋爱的第 {loveDay} ❤️ \n" +
                "{girlBirthday} \n" +
                "{boyBirthday} \n" +
                "\n" +
                "🌈: {rainbowFart}";
    }

    @Test
    public void mytest(){
        BigDecimal big1 = new BigDecimal(3000);
        BigDecimal big2 = new BigDecimal(3400);
        System.out.println(big1.compareTo(big2));
    }

    @Test
    public String getSZ() {
        WebDriver webDriver = getWebDriver();
        webDriver.get("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml");
        String pageSource = webDriver.getTitle();
        webDriver.quit();
        List<String> split = StrUtil.split(pageSource, '(');
        return split.get(0);
    }

    @Test
    public void mysend() {
        WebDriver webDriver = getWebDriver();
        webDriver.get("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml");
        String pageSource = webDriver.getPageSource();
        webDriver.quit();
        System.out.println(pageSource);
    }

    public static void main(String[] args) throws IOException {
        try (WebClient webClient = getWebClient()) {
            HtmlPage page = webClient.getPage("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml");

        }

        // 屏蔽HtmlUnit等系统 log
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

        String url = "https://quote.eastmoney.com/zs000001.html";
        System.out.println("Loading page now-----------------------------------------------: "+url);

        // HtmlUnit 模拟浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
        webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(10 * 1000);                   // 设置连接超时时间
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(30 * 1000);               // 等待js后台执行30秒

        String pageAsXml = page.asXml();

        // Jsoup解析处理
        Document doc = Jsoup.parse(pageAsXml, "https://quote.eastmoney.com/zs000001.html");
//        Elements pngs = doc.select("img[src$=.png]");                   // 获取所有图片元素集
        // 此处省略其他操作
        System.out.println(doc.toString());
    }

    @Test
    public void joupTest() throws IOException {
//        Document doc = Jsoup.connect("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml").get();
//        String title = doc.html();
//        System.out.println("Title is: " + title);


        WebDriver webDriver = getWebDriver();
        webDriver.get("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml");
        String pageSource = webDriver.getTitle();
        System.out.println(pageSource);
    }

//    public static void main(String[] args) {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml"))
//                .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response.body());
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public static WebClient getWebClient() {
        /*WebClient webClient = new WebClient(BrowserVersion.CHROME,"127.0.0.1",8001,"http");*/
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        //运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //禁用CSS，可避免自动二次请求css进行渲染
        webClient.getOptions().setCssEnabled(false);
        //启动JS
        webClient.getOptions().setJavaScriptEnabled(true);
        //启动客户端重定向
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setActiveXNative(false);
        //禁用日志打印
        Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        //设置超时
        webClient.getOptions().setTimeout(10000);
        //忽略证书
        webClient.getOptions().setUseInsecureSSL(true);
        //设置cookie
        webClient.getCookieManager().setCookiesEnabled(true);
        return webClient;
    }

    @Test
    public void test11() {
        WebDriver webDriver = WebDriverFactory.getWebDriver();
        webDriver.close();
        WebDriver webDriver1 = WebDriverFactory.getWebDriver();
        webDriver.close();
        WebDriver webDriver2 = WebDriverFactory.getWebDriver();
        webDriver.close();
    }


    public void test1() throws IOException {
        Connection connection = Jsoup.connect("https://example.com/login");

        // 设置请求参数
        connection.data("username", "your_username");
        connection.data("password", "your_password");

        // 发送POST请求并获取响应
        Connection.Response response = connection.method(Connection.Method.POST).execute();

        // 处理响应
        if (response.statusCode() == 200) {
            // 登录成功，执行操作
            Document document = response.parse();
            // 进一步操作，例如获取账户信息
            // ...
        } else {
            // 登录失败
            System.out.println("登录失败");
        }
    }

    @Test
    public static void test2() throws IOException {
        Document document = Jsoup.connect("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml").get();
        System.out.println(document.body());
    }

    @Test
    public static void test3() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, "utf-8");
        response.close();
        httpClient.close();

        Document parse = Jsoup.parse(content);
        System.out.println(parse);
    }

    private final static String webDriverName = "webdriver.chrome.driver";
    private final static String webDriverPath ="D:\\sgh\\other\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";

    public WebDriver getWebDriver() {
        System.setProperty(webDriverName, webDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        return new ChromeDriver(options);
    }
    public String getShSz(){
        WebDriver webDriver = getWebDriver();
        webDriver.get("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml");
        WebElement webElement = webDriver.findElement(By.id("globalIndexScroller0"));
        String text = webElement.getText();
        webDriver.quit();
        return text;
    }

    @SneakyThrows
    @Test
    public void mytest1(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> "三友");
        System.out.println(future.get());
        executorService.shutdown();
    }
}
