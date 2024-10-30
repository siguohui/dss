package com.xaiaosi.i18n.repository;

import com.xaiaosi.i18n.model.I18nMsg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface I18nMsgRepository extends JpaRepository<I18nMsg,Long> {

    I18nMsg findByCodeAndLocale(String code, String locale);
}
