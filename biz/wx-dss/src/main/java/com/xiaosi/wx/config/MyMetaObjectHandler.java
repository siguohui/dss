package com.xiaosi.wx.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Description: mybatis自动填充功能
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_TIME_FIELD = "createTime";
    private static final String UPDATE_TIME_FIELD = "updateTime";
    private static final String UTC = "UTC";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATE_TIME_FIELD, LocalDateTime.class, LocalDateTime.now (ZoneId.of (UTC)));
        this.strictUpdateFill(metaObject, UPDATE_TIME_FIELD,  LocalDateTime.class, LocalDateTime.now (ZoneId.of (UTC)));
        this.strictInsertFill(metaObject, "createBy", String.class, "currentUser");
        /*this.setFieldValByName(UPDATE_TIME_FIELD,new Date(),metaObject);*/
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, UPDATE_TIME_FIELD, LocalDateTime.class, LocalDateTime.now (ZoneId.of (UTC)));
        this.strictUpdateFill(metaObject, "updateBy", String.class, "currentUser");
        /*this.setFieldValByName(UPDATE_TIME_FIELD,new Date(),metaObject);*/
    }
}
