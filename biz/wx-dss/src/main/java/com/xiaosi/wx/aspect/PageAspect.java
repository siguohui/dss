package com.xiaosi.wx.aspect;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaosi.wx.annotation.PageX;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class PageAspect {

    @Pointcut("@annotation(com.xiaosi.wx.annotation.PageX)")
    public void point(){}

    @Around("point()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");

        if (pageSize !=null && pageNo!= null){
            int page_no = Integer.valueOf(pageNo);
            int page_size = Integer.valueOf(pageSize);
            PageHelper.startPage(page_no,page_size);
        }

        Object proceed = pjp.proceed();
        if (proceed instanceof Page){
            proceed = (Page) proceed;
        }

        System.out.println("环绕后");
        return proceed;
    }
}
