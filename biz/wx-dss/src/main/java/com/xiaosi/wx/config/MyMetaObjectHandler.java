package com.xiaosi.wx.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xiaosi.wx.utils.JwtUtil;
import com.xiaosi.wx.utils.SecurityUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.sql.RowSet;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @Description: mybatis自动填充功能
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Resource
    private JwtUtil jwtUtil;

    private static final String CREATE_TIME_FIELD = "createTime";
    private static final String CREATE_BY_FIELD = "createBy";
    private static final String UPDATE_TIME_FIELD = "updateTime";
    private static final String UPDATE_BY_FIELD = "updateBy";
    private static final String UTC = "UTC";

    @Override
    public void insertFill(MetaObject metaObject) {
        String  username;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        System.out.println("----------------------------------");

        System.out.println(SecurityUtils.getSession());

        System.out.println("----------------------------------");



        this.strictInsertFill(metaObject, CREATE_TIME_FIELD, LocalDateTime.class, LocalDateTime.now (ZoneId.of (UTC)));
        this.strictUpdateFill(metaObject, UPDATE_TIME_FIELD,  LocalDateTime.class, LocalDateTime.now (ZoneId.of (UTC)));
        this.strictInsertFill(metaObject, CREATE_BY_FIELD, String.class, username);
        this.strictInsertFill(metaObject, UPDATE_BY_FIELD, String.class, username);
        /*this.setFieldValByName(UPDATE_TIME_FIELD,new Date(),metaObject);*/
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String  username;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDetails){
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        this.strictUpdateFill(metaObject, UPDATE_TIME_FIELD, LocalDateTime.class, LocalDateTime.now (ZoneId.of (UTC)));
        this.strictUpdateFill(metaObject, "updateBy", String.class, username);
        /*this.setFieldValByName(UPDATE_TIME_FIELD,new Date(),metaObject);*/
    }
}
