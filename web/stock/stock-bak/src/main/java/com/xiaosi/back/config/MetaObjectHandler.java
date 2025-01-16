package com.xiaosi.back.config;

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
public class MetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {

//    @Resource
//    TokenUtils tokenUtils;

    private static final String CREATE_TIME_FIELD = "createTime";
    private static final String CREATE_BY_FIELD = "createBy";
    private static final String UPDATE_TIME_FIELD = "updateTime";
    private static final String UPDATE_BY_FIELD = "updateBy";
    private static final String UTC = "UTC";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATE_TIME_FIELD, LocalDateTime.class, LocalDateTime.now (ZoneId.of (UTC)));
        this.strictUpdateFill(metaObject, UPDATE_TIME_FIELD,  LocalDateTime.class, LocalDateTime.now (ZoneId.of (UTC)));
        this.strictInsertFill(metaObject, CREATE_BY_FIELD, Long.class, 1L);
        this.strictInsertFill(metaObject, UPDATE_BY_FIELD, Long.class, 1L);
        /*this.setFieldValByName(UPDATE_TIME_FIELD,new Date(),metaObject);*/
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        String  username;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//
//        if(principal instanceof UserDetails){
//            username = ((UserDetails) principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
        this.strictUpdateFill(metaObject, UPDATE_TIME_FIELD, LocalDateTime.class, LocalDateTime.now (ZoneId.of (UTC)));
        this.strictUpdateFill(metaObject, "updateBy", Long.class, 1L);
        /*this.setFieldValByName(UPDATE_TIME_FIELD,new Date(),metaObject);*/
    }
}
