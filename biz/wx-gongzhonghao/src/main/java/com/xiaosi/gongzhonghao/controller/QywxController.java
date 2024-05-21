package com.xiaosi.gongzhonghao.controller;

import com.xiaosi.gongzhonghao.QywxService.IQYWeiXinLoginService;
import com.xiaosi.gongzhonghao.seervice.QywxService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class QywxController {
    @Autowired
    private QywxService qywxService;
    @Resource
    private IQYWeiXinLoginService qyWeiXinLoginService;


    @GetMapping(value = "/wx/check1/")
    public WxJsapiSignature getJsapiSignature(@RequestParam(value = "url",required = false) String url) {
        try {  // 直接调用wxMpServer 接口
            System.out.println("访问WxJsapiSignature=====/system/qywx/signature/" + url + "");
            WxJsapiSignature wxJsapiSignature = qywxService.getJsapiSignture(url);
            System.out.println("AppId===" + wxJsapiSignature.getAppId());
            System.out.println("Timestamp===" + wxJsapiSignature.getTimestamp());
            System.out.println("NonceStr===" + wxJsapiSignature.getNonceStr());
            System.out.println("Signature===" + wxJsapiSignature.getSignature());
            return wxJsapiSignature;
        } catch (WxErrorException e) {
            return new WxJsapiSignature();
        }
    }

    /**
     * 验证URL有效性
     *
     */
    @GetMapping("/wx/check/")
    public void verifyURLValidity(@RequestParam(value = "msg_signature", required = true) String msgSignature,
        @RequestParam(value = "timestamp", required = true) String timestamp,
        @RequestParam(value = "nonce", required = true) String nonce,
        @RequestParam(value = "echostr", required = true) String echostr,
        HttpServletResponse response) {

        log.info("----------微信接口访问日志----------");

        log.info("msg_signature:{},timestamp:{},nonce:{},echostr:{}",msgSignature,timestamp,nonce,echostr);
        String msg = qyWeiXinLoginService.verifyURLValidity(msgSignature, timestamp, nonce, echostr);
        log.info("明文:{}",msg);
        log.info("----------微信接口访问结束----------");
        try {
            response.getWriter().print(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}
