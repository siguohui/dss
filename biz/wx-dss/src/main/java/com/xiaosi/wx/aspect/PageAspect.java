package com.xiaosi.wx.aspect;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaosi.wx.page.BasePage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Aspect
@Component
public class PageAspect {

    @Pointcut("@annotation(com.xiaosi.wx.page.annotation.PageX) || execution(* *..*Service.*Page(..))")
    public void point(){}

    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        BasePage basePage = null;
        for (Object param : args) {
            if (param instanceof BasePage) {
                basePage = (BasePage) param;
                break;
            }
        }
        if (basePage != null && basePage.isPage()) {
            PageHelper.startPage(basePage.getNo(), basePage.getSize());
        }
        Object returnValue = joinPoint.proceed(args);
        if (basePage != null && basePage.isPage()) {
            if (null != returnValue && returnValue instanceof List) {
                List list = (List) returnValue;
                PageInfo pageInfo = new PageInfo(list);
                basePage.setTotal((int) pageInfo.getTotal());
            }
        }
        return returnValue;
    }
}
