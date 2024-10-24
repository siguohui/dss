package org.xiaosi.stomp.schedule;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

public class WebMagicSpider implements PageProcessor {

    private Site site;

    public WebMagicSpider() {
        // 配置爬虫的User-Agent和其他参数
        site = Site.me()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
                .setRetryTimes(3)
                .setSleepTime(1000);
    }

    @Override
    public void process(Page page) {
        // 解析网页，提取需要的数据
        String title = page.getHtml().xpath("//title/text()").get();
        System.out.println("Title: " + title);

        // 如果需要爬取其他页面，可以添加新的链接到Scheduler中
        // page.addTargetRequest("https://example.com/other-page");
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        // 创建爬虫
        Spider spider = Spider.create(new WebMagicSpider())
                .addUrl("https://finance.sina.com.cn/realstock/company/sh000001/nc.shtml")
                .thread(5)
                .setScheduler(new QueueScheduler());

        // 运行爬虫
        spider.run();
    }
}
