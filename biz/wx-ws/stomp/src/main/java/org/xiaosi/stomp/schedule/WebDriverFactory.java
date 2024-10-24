package org.xiaosi.stomp.schedule;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    private final static String webDriverName = "webdriver.chrome.driver";
    private final static String webDriverPath ="D:\\sgh\\other\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";

    private WebDriverFactory() {
    }

    private static volatile WebDriver webDriver = null;

    public static WebDriver getWebDriver() {
        if (webDriver == null) {
            synchronized (WebDriverFactory.class) {
                if (webDriver == null) {
                    System.setProperty(webDriverName, webDriverPath);
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--headless");
                    webDriver=new ChromeDriver(options);
                }
            }
        }
        return webDriver;
    }
}
