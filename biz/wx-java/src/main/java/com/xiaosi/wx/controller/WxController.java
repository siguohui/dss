package com.xiaosi.wx.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.*;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
public class WxController {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpMessageRouter wxMpMessageRouter;

    /**
     * 验证消息的确来自微信服务器
     * <p>
     * 开发者通过检验signature对请求进行校验。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param //echostr   随机字符串
     * @return
     */
    @GetMapping("/send")
    public String configAccess(String signature, String timestamp, String nonce, String echostr) {
        // 校验签名
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("签名校验 ===》 非法请求");
            // 消息签名不正确，说明不是公众平台发过来的消息
            return null;
        }

        log.error("签名校验 ===》 验证成功");
        // 返回echostr
        return echostr;
    }

//    @RequestMapping("/getInfo")
    public String getInfo(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) throws WxErrorException {

        WxMpUserList wxMpUserList = wxMpService.getUserService().userList();

        System.out.println(wxMpUserList.getOpenids());


        System.out.println("token---" + wxMpService.getAccessToken());;

        String lang = "zh_CN"; //语言 openId 可以从关注用户列表接口来或者是用户发送消息的fromuser
        WxMpUser user = wxMpService.getUserService().userInfo(wxMpUserList.getOpenids().get(0), lang);
        System.out.println(user);
        System.out.println(user.toString());

        // 校验签名
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("签名校验 ===》 非法请求");
            // 消息签名不正确，说明不是公众平台发过来的消息
            return null;
        }
        log.error("签名校验 ===》 验证成功");

        // 解析消息体，封装为对象
        WxMpXmlMessage xmlMessage = WxMpXmlMessage.fromXml(requestBody);

        WxMpXmlOutMessage outMessage = null;
        try {
            // 将消息路由给对应的处理器，获取响应
            outMessage = wxMpMessageRouter.route(xmlMessage);
        } catch (Exception e) {
            log.error("消息路由异常", e);
        }
        // 将响应消息转换为xml格式返回
        return outMessage == null ? null : outMessage.toXml();

        // 接收消息内容
//        String inContent = xmlMessage.getContent();

        // 响应的消息内容
//        String outContent;
//        // 根据不同的关键字回复消息
//        if (inContent.contains("hello")) {
//            outContent = "hello world";
//        } else if (inContent.contains("java")) {
//            outContent = "hello java";
//        } else {
//            outContent = "服务繁忙,暂时不能回复";
//        }

        // 构造响应消息对象
//        WxMpXmlOutTextMessage outTextMessage = WxMpXmlOutMessage.TEXT().content(outContent).fromUser(xmlMessage.getToUser())
//                .toUser(xmlMessage.getFromUser()).build();

        // 将响应消息转换为xml格式返回
//        return outTextMessage.toXml();
    }

    /**
     * 网页授权
     * 1 第一步：用户同意授权，获取code
     * 2 第二步：通过code换取网页授权access_token
     * 3 第三步：刷新access_token（如果需要）
     * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
     * @param code
     * @return
     */
    @GetMapping("/getCode")
    @ResponseBody
    public String getCode(String code)throws Exception{
        //1 第一步：用户同意授权，获取code
        System.out.println(code);
        //2 第二步：通过code换取网页授权access_token
        WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
        //4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
        WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);
        return userInfo.toString();
    }

    @PostMapping("/send")
    public String send(HttpServletRequest request) throws WxErrorException, IOException {
        //获取消息流,并解析xml
        WxMpXmlMessage message=WxMpXmlMessage.fromXml(request.getInputStream());
        System.out.println(message.toString());
        //消息类型
        String messageType=message.getMsgType();
        System.out.println("消息类型:"+messageType);
        //发送者帐号
        String fromUser=message.getFromUser();
        System.out.println("发送者账号："+fromUser);
        //开发者微信号
        String touser=message.getToUser();
        System.out.println("开发者微信："+touser);
        //文本消息  文本内容
        String text=message.getContent();
        System.out.println("文本消息："+text);

        //获取微信服务器的IP地址
        /*String[] callbackIP = wxMpService.getCallbackIP();
        for(int i=0;i<callbackIP.length;i++){
            System.out.println("IP地址"+i+"："+callbackIP[i]);
        }*/

        /**
         * 文本消息
         */
        if(messageType.equals("text")){
            //如果用户输入的是登录，那么进行网页授权
            if(text.equals("登录")){
                //构造网页授权url
                String redirect_uri="http://117.72.71.62:7500/getCode";//回调的url
                String url = wxMpService.getOAuth2Service().buildAuthorizationUrl(redirect_uri, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
                WxMpXmlOutTextMessage texts=WxMpXmlOutTextMessage
                        .TEXT()
                        .toUser(fromUser)
                        .fromUser(touser)
                        .content("点击<a href=\""+url+"\">这里</a>登录")
                        .build();
                String result = texts.toXml();
                System.out.println("响应给用户的消息："+result);
                return result;
            }
            WxMpXmlOutTextMessage texts=WxMpXmlOutTextMessage
                    .TEXT()
                    .toUser(fromUser)
                    .fromUser(touser)
                    .content("欢迎光临，热烈欢迎")
                    .build();
            String result = texts.toXml();
            System.out.println("响应给用户的消息："+result);
            return result;
        }

          /*  WxMpXmlOutTextMessage texts=WxMpXmlOutTextMessage
                    .TEXT()
                    .toUser(fromUser)
                    .fromUser(touser)
                    .content("欢迎光临，热烈欢迎")
                    .build();
            String result = texts.toXml();
            System.out.println("响应给用户的消息："+result);
            return result;
        }*/

        /**
         * 图片消息
         */
        if(messageType.equals("image")){
            //创建file对象
            File file=new File("D:\\sgh\\image\\1.jpg");
            //上传多媒体文件
            WxMediaUploadResult wxMediaUploadResult = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE, file);
            WxMpXmlOutImageMessage images = WxMpXmlOutMessage.IMAGE()
                    .mediaId(wxMediaUploadResult.getMediaId())//获取上传到微信服务器的临时素材mediaid.
                    .fromUser(touser)
                    .toUser(fromUser)
                    .build();
            String result = images.toXml();
            System.out.println("响应给用户的消息："+result);
            return result;
        }
        /**
         * 音乐
         */
        if(messageType.equals("misic")){
            //创建file对象
            File file=new File("E:\\music\\qx.mp3");
            //上传多媒体文件
            WxMediaUploadResult wxMediaUploadResult = wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.VOICE, file);
            WxMpXmlOutMusicMessage musics = WxMpXmlOutMessage.MUSIC()
                    .fromUser(fromUser)
                    .toUser(touser)
                    .title("枪声")
                    .description("最强震撼枪声")
                    .hqMusicUrl("高质量音乐链接，WIFI环境优先使用该链接播放音乐")
                    .musicUrl("缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id")
                    .thumbMediaId(wxMediaUploadResult.getMediaId())
                    .build();
            String result = musics.toXml();
            System.out.println("响应给用户的消息："+result);
            return result;
        }
        return null;
    }

//    @PostMapping("/send")
    public String send(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) throws WxErrorException {

        WxMpUserList wxMpUserList = wxMpService.getUserService().userList();

        System.out.println(wxMpUserList.getOpenids());


        System.out.println("token---" + wxMpService.getAccessToken());;

        String lang = "zh_CN"; //语言 openId 可以从关注用户列表接口来或者是用户发送消息的fromuser
        WxMpUser user = wxMpService.getUserService().userInfo(wxMpUserList.getOpenids().get(0), lang);
        System.out.println(user);
        System.out.println(user.toString());

        // 校验签名
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("签名校验 ===》 非法请求");
            // 消息签名不正确，说明不是公众平台发过来的消息
            return null;
        }
        log.error("签名校验 ===》 验证成功");

        // 解析消息体，封装为对象
        WxMpXmlMessage xmlMessage = WxMpXmlMessage.fromXml(requestBody);

        WxMpXmlOutMessage outMessage = null;
        try {
            // 将消息路由给对应的处理器，获取响应
            outMessage = wxMpMessageRouter.route(xmlMessage);
        } catch (Exception e) {
            log.error("消息路由异常", e);
        }
        // 将响应消息转换为xml格式返回
        return outMessage == null ? null : outMessage.toXml();

        // 接收消息内容
//        String inContent = xmlMessage.getContent();

        // 响应的消息内容
//        String outContent;
//        // 根据不同的关键字回复消息
//        if (inContent.contains("hello")) {
//            outContent = "hello world";
//        } else if (inContent.contains("java")) {
//            outContent = "hello java";
//        } else {
//            outContent = "服务繁忙,暂时不能回复";
//        }

        // 构造响应消息对象
//        WxMpXmlOutTextMessage outTextMessage = WxMpXmlOutMessage.TEXT().content(outContent).fromUser(xmlMessage.getToUser())
//                .toUser(xmlMessage.getFromUser()).build();

        // 将响应消息转换为xml格式返回
//        return outTextMessage.toXml();
    }

   /* @RequestMapping("send")
    public String send(@RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {
        // 校验签名
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("签名校验 ===》 非法请求");
            // 消息签名不正确，说明不是公众平台发过来的消息
            return null;
        }
        log.error("签名校验 ===》 验证成功");

        // 解析消息体，封装为对象
        WxMpXmlMessage xmlMessage = WxMpXmlMessage.fromXml(requestBody);
        // 接收消息内容
        String inContent = xmlMessage.getContent();

        // 响应的消息内容
        String outContent;
        // 根据不同的关键字回复消息
        if (inContent.contains("hello")) {
            outContent = "hello world";
        } else if (inContent.contains("java")) {
            outContent = "hello java";
        } else {
            outContent = "服务繁忙,暂时不能回复";
        }

        // 构造响应消息对象
        WxMpXmlOutTextMessage outTextMessage = WxMpXmlOutMessage.TEXT().content(outContent).fromUser(xmlMessage.getToUser())
                .toUser(xmlMessage.getFromUser()).build();

        // 将响应消息转换为xml格式返回
        return outTextMessage.toXml();
    }*/
}
