package com.xiaosi.wx.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.xiaosi.wx.mapper.SysMenuMapper;
import com.xiaosi.wx.entity.SysMenu;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WxDssAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Resource
    private SysMenuMapper menuMapper;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> auth, RequestAuthorizationContext requestAuthorizationContext) {
        Authentication authentication = auth.get();
        if(Objects.isNull(authentication)){
            return new AuthorizationDecision(false);
        }
        Set<String> set = authentication.getAuthorities().stream().map(m -> m.getAuthority()).collect(Collectors.toSet());
        if(set.size()==1&&set.contains("ROLE_ANONYMOUS")){
            return new AuthorizationDecision(false);
        }

        //获取请求路径
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String uri = request.getRequestURI();
        if("/login".equals(uri) || "/logout".equals(uri) || "/error".equals(uri)){
        return new AuthorizationDecision(true);
        }

        SysMenu menu = new LambdaQueryChainWrapper<>(menuMapper).eq(SysMenu::getPath, uri).one();

//        menuMapper.selectOne(new QueryWrapper<SysMenu>().eq("path", uri));

        if(Objects.isNull(menu)) {
            return new AuthorizationDecision(true);
        }

        String menuPerm = menu.getPerms();
        if(StringUtils.isBlank(menuPerm)){
            return new AuthorizationDecision(true);
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String userPerm = authority.getAuthority();
            if(userPerm.equals(menuPerm)){
                return new AuthorizationDecision(true);
            }

        }
        return new AuthorizationDecision(false);
    }
}
