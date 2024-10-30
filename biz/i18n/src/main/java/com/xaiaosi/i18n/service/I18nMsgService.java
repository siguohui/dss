package com.xaiaosi.i18n.service;

import com.xaiaosi.i18n.model.I18nMsg;

public interface I18nMsgService {

    I18nMsg findByCodeAndLocale(String code, String locale);
}
