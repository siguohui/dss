//package com.xiaosi.authorization
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Component
//
//
//@Component
//public class CustomerFilter implements FilterInvocationSecurityMetadataSource {
//    @Autowired
//    private IMenuService menuService;
//    AntPathMatcher antPathMatcher = new AntPathMatcher();
//
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        //获取到登录请求的url
//        String url = ((FilterInvocation) object).getRequestUrl();
//        for (String s : UrlConstant.urls) {
//        if(antPathMatcher.match(s,url)){
//            return null;
//        }
//    }
//        List<Menu> menuList = menuService.getMenuWithRole();
//        for (Menu menu : menuList) {
//        if (antPathMatcher.match(menu.getUrl(), url)) {
//            String[] strings = menu.getRoles().stream().map(it -> {
//                return it.getName();
//            }).toArray(String[]::new);
//            //转换为
//            return SecurityConfig.createList(strings);
//        }
//    }
//        return SecurityConfig.createList("ROLE_LOGIN");
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//}
