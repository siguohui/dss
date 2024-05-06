package com.xiaosi.wx.handler;

import com.xiaosi.wx.annotation.Policy;
import com.xiaosi.wx.enums.PolicyEnum;
import com.xiaosi.wx.model.SysUser;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.function.Supplier;

public class CustomAuthorizationManager<T> implements AuthorizationManager<MethodInvocation> {
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation methodInvocation) {
        if (hasAuthentication(authentication.get())) {
            Policy policyAnnotation = AnnotationUtils.findAnnotation(methodInvocation.getMethod(), Policy.class);
            SysUser user = (SysUser) authentication.get().getPrincipal();
            return new AuthorizationDecision(Optional.ofNullable(policyAnnotation)
                    .map(Policy::value).filter(policy -> policy == PolicyEnum.OPEN
                            || (policy == PolicyEnum.RESTRICTED && user.isAccessPolicy())).isPresent());
        }
        return new AuthorizationDecision(false);
    }

    private boolean hasAuthentication(Authentication authentication) {
        return authentication != null  && authentication.isAuthenticated();
//        return authentication != null && isNotAnonymous(authentication) && authentication.isAuthenticated();
    }

   /* private boolean isNotAnonymous(Authentication authentication) {
        return !this.trustResolver.isAnonymous(authentication);
    }*/
}
