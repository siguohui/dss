package com.xaiaosi.i18n.i18n.impl;

import com.xaiaosi.i18n.i18n.I18DynamicMessage;
import com.xaiaosi.i18n.i18n.I18nDynamicMsgProvider;
import com.xaiaosi.i18n.model.I18nMsg;
import com.xaiaosi.i18n.service.I18nMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MyI18nDynamicMsgProvider implements I18nDynamicMsgProvider {

    @Autowired
    private I18nMsgService i18nMsgService;

    @Override
    public I18DynamicMessage getDynamicMsg(Locale locale, String code) {
        I18DynamicMessage i18DynamicMessage = new I18DynamicMessage();
        I18nMsg i18nMsgEntity = i18nMsgService.findByCodeAndLocale(code, locale.toString());
        if (i18nMsgEntity != null) {
            String msg = i18nMsgEntity.getMsg();
            i18DynamicMessage.setMsg(msg);
            i18DynamicMessage.setCode(code);
            i18DynamicMessage.setLocale(locale.toString());
        }
        return i18DynamicMessage;
    }
}
