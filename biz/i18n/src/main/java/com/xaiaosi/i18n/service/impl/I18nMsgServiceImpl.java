package com.xaiaosi.i18n.service.impl;

import com.xaiaosi.i18n.model.I18nMsg;
import com.xaiaosi.i18n.repository.I18nMsgRepository;
import com.xaiaosi.i18n.service.I18nMsgService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class I18nMsgServiceImpl implements I18nMsgService {

    @Resource
    private I18nMsgRepository i18nMsgRepository;

    @Override
    public I18nMsg findByCodeAndLocale(String code, String locale) {
        return i18nMsgRepository.findByCodeAndLocale(code, locale);
    }
}
