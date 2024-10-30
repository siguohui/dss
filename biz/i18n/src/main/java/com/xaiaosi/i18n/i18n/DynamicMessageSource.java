package com.xaiaosi.i18n.i18n;

import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@Component
public class DynamicMessageSource extends AbstractMessageSource {

    private final I18nDynamicMsgProvider i18nMessageProvider;

    public DynamicMessageSource(I18nDynamicMsgProvider i18nMessageProvider) {
        this.i18nMessageProvider = i18nMessageProvider;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        I18DynamicMessage i18nMessage = i18nMessageProvider.getDynamicMsg(locale, code);
        if (i18nMessage != null) {
            return createMessageFormat(i18nMessage.getMsg() , locale);
        }
        return null;
    }

















}
