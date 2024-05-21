package com.xiaosi.gongzhonghao.QywxService;

public interface IQYWeiXinLoginService {
    String verifyURLValidity(String msgSignature, String timestamp, String nonce, String echostr);
}
