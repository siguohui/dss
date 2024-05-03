//package com.xiaosi.authorization;
//
//@Component
//public class CustomerDecisionFilter implements AccessDecisionManager {
//    @Override
//    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//        if(configAttributes.isEmpty()||configAttributes.size()==0){
//            return;
//        }
//        for (ConfigAttribute configAttribute : configAttributes) {
//            //这就是当前url需要的角色，满足其中一个角色就可以了
//            String attribute = configAttribute.getAttribute();
//            if("ROLE_LOGIN".equals(attribute)){
//                //判断当前是否登录，如果没有登录
//                return;
//            }
//            //取出当前的角色
//            //TODO
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                String authorityAuth = authority.getAuthority();
//                if(authorityAuth.equals(attribute)){
//                    return;
//                }
//            }
//        }
//        throw new AccessDeniedException("权限不足");
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute attribute) {
//        return false;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return false;
//    }
//}
