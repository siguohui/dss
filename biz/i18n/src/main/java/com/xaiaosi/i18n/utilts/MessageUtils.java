package com.xaiaosi.i18n.utilts;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.WebApplicationObjectSupport;

/**
 * 获取i18n资源文件
 *
 * @author cccl
 */
public class MessageUtils extends WebApplicationObjectSupport {
    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
   /* public String message(String code, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }*/
}
