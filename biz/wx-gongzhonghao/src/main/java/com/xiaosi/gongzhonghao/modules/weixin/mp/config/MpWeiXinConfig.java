package com.xiaosi.gongzhonghao.modules.weixin.mp.config;

import com.xiaosi.gongzhonghao.config.api.WeiXinProperties;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-18 23:31
 */
@Configuration
@RequiredArgsConstructor
public class MpWeiXinConfig {

    private final WeiXinProperties weiXinProperties;

    @Bean
    public WxMpService wxMpService() {
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(weiXinProperties.getAppId()); // 设置微信公众号的appid
        config.setSecret(weiXinProperties.getAppSecret()); // 设置微信公众号的app corpSecret

        // 实际项目中请注意要保持单例，不要在每次请求时构造实例，具体可以参考demo项目
        WxMpService wxService = new WxMpServiceImpl();
        wxService.setWxMpConfigStorage(config);
        return wxService;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }


}
