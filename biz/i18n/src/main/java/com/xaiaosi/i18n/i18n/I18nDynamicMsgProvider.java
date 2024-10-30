package com.xaiaosi.i18n.i18n;

import java.util.Locale;

public interface I18nDynamicMsgProvider {

    /**
     * 获取动态国际化信息
     * @param locale 指定区域
     * @param code 国际化code
     * @return
     */
    I18DynamicMessage getDynamicMsg(Locale locale, String code);
}
