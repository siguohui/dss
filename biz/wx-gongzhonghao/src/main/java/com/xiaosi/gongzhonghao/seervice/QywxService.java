package com.xiaosi.gongzhonghao.seervice;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.stereotype.Service;

@Service
public class QywxService {
    //获取对应应用的签名
    public WxJsapiSignature getJsapiSignture(String url) throws WxErrorException {
        // 替换成自己应用的appId和secret，agentId
        Integer agentId = 1111111;
        String corpId="XXXXXXXX";
        String corpSecret = "XXXXXXXX";
        WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
        config.setCorpId(corpId);      // 设置微信企业号的appid
        config.setCorpSecret(corpSecret);  // 设置微信企业号的app corpSecret
        config.setAgentId(agentId);     // 设置微信企业号应用ID
        WxCpServiceImpl wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(config);
        System.out.println("WxJsapiSignature===url==="+url);
        WxJsapiSignature wxJsapiSignature = wxCpService.createJsapiSignature(url);
        //wxJsapiSignature中可以直接获取签名信息 且方法内部添加了缓存功能
        return wxJsapiSignature;
    }

}
