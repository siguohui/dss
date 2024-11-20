package org.xiaosi.stomp.work.config;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author kcaco
 * @since 2022-08-19 17:41
 */
@Configuration
@RequiredArgsConstructor
public class WorkWeiXinConfig {

    private final WorkWeiXinProperties workWeiXinProperties;

    @Bean
    public WxCpServiceImpl wxCpService() {
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(workWeiXinProperties.getCorpId());      // 设置微信企业号的appid
        config.setCorpSecret(workWeiXinProperties.getCorpSecret());  // 设置微信企业号的app corpSecret
        config.setAgentId(workWeiXinProperties.getAgentId());     // 设置微信企业号应用ID

        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
        return wxCpService;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
