package com.xiaosi.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WxJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxJavaApplication.class,args);
    }



//    WxMpOAuth2Service wxMpOAuth2Service = wxService.getOAuth2Service();
//    String url = wxMpOAuth2Service.buildAuthorizationUrl(redirectUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
//
//
//
//    WxMpConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
//    wxMpConfigStorage.setAppId("your_app_id");wxMpConfigStorage.setSecret("your_app_secret");
//    WxMpService wxMpService = new WxMpServiceImpl();wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
//
//
//    response.sendRedirect(url);
//
//
//
//    WxMpOAuth2AccessToken accessToken = wxMpOAuth2Service.getAccessToken(code);
//    WxMpUser wxMpUser = wxService.getUserService().userInfo(accessToken.getOpenId(), null);
}
