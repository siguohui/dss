package com.xiaosi.gongzhonghao.QywxService.impl;

import com.xiaosi.gongzhonghao.QywxService.IQYWeiXinLoginService;
import com.xiaosi.gongzhonghao.exception.AesException;
import com.xiaosi.gongzhonghao.utils.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QYWeiXinLoginServiceImpl implements IQYWeiXinLoginService {
    private static final Logger logger = LoggerFactory.getLogger(QYWeiXinLoginServiceImpl.class);

    @Override
    public String  verifyURLValidity(String msgSignature, String timestamp, String nonce, String echostr) {
        logger.info("企业微信登录获取到的参数：msgSignature:{},timestamp:{},nonce:{},echostr:{}:",msgSignature,timestamp,nonce,echostr);
        //token
        String TOKEN = "LRXggpnjUVJazNLLoAMrgN";
        // encodingAESKey
        String ENCODINGAES_KEY = "JyiX9BYtr0TyeQaoDy9odgXur21TaNblCU2T2lOyUpq";
        //企业ID
        String CORP_ID = "ww21bfd7bab878da9c";

        // 通过检验msg_signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(TOKEN, ENCODINGAES_KEY, CORP_ID);
            result = wxcpt.VerifyURL(msgSignature, timestamp, nonce, echostr);
        } catch (AesException e) {
            e.printStackTrace();
        }
        logger.info("----------微信接口访问结束----------");
        return result;
    }
}
