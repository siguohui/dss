AbstractUserDetailsAuthenticationProvider
MessageSourceAware 国际化相关的接口
public class DemoBean implements MessageSourceAware {
    private MessageSource messageSource;
    @Override
    public void setMessageSource(MessageSource messageSource) {
        ReloadableResourceBundleMessageSource bundleSource = new ReloadableResourceBundleMessageSource();
        bundleSource.setBasename("messages");
        this.messageSource = bundleSource;
    }
    public void printMessage() {
        String code = "msg";
        Object[] args = new Object[]{"cheney"};
        Locale locale = new Locale("en", "US");
        String msg = messageSource.getMessage(code, args, locale);
        System.out.println(msg);
    }
}


PathPattern，AntPathMatcher

我们知道/**和/{*pathVariable}都有匹配剩余所有path的“能力”，那它俩到底有什么区别呢?

/**能匹配成功，但无法获取到动态成功匹配元素的值
/{*pathVariable}可认为是/**的加强版：可以获取到这部分动态匹配成功的值
正所谓一代更比一代强嘛，如是而已。

和**的优先级关系
既然/**和/{*pathVariable}都有匹配剩余path的能力，那么它俩若放在一起，优先级关系是怎样的呢?
结论：当二者同时出现(出现冲突)时，/**优先匹配。

ConfigAttribute
AccessDecisionVoter
![img.png](img.png)


在 5.6 之前，使用 @EnableGlobalMethodSecurity 注解是标准的做法，在 5.6 之后，@EnableMethodSecurity 引入了一种更灵活的方法来配置方法安全授权（Method Security）。
在本教程中，我们将通过示例代码了解 @EnableMethodSecurity 如何代替 @EnableGlobalMethodSecurity，以及他们之间的区别。


@EnableMethodSecurity


```angular2html
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationConverter converter) throws Exception {
        if (officeSecurityProperties.isEnable()) {
            http.authorizeHttpRequests(
                            authorize -> authorize
                                    .requestMatchers("/doc.html", "/webjars/**", "/v3/api-docs/**").permitAll()
                                    .anyRequest().authenticated()
                    )
                    .oauth2ResourceServer(
                            oauth2 -> oauth2.jwt(
                                    jwt -> jwt.jwtAuthenticationConverter(converter)
                            )
                    );
        } else {
            http.securityContext(AbstractHttpConfigurer::disable);
        }
        return http.build();
    }
```
```angular2html
@Bean
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConditionalOnProperty("office.security.property")
static MethodInterceptor preAuthorizeAuthorizationMethodInterceptor() {
    return AuthorizationManagerBeforeMethodInterceptor.preAuthorize();
}
```


https://cloud.tencent.com/developer/article/1942239

我们只需要实现钩子方法check就可以了，它将当前提供的认证信息authentication和泛化对象T进行权限检查，
并返回AuthorizationDecision，AuthorizationDecision.isGranted将决定是否能够访问当前资源。AuthorizationManager提供了两种使用方式。

基于配置
为了使用AuthorizationManager，引入了相关配置是AuthorizeHttpRequestsConfigurer，这个配置类非常类似于第九章中的基于表达式的访问控制。

@PreAuthorize("#username == authentication.principal.username")
@PostAuthorize("returnObject.username == authentication.principal.nickName")
public CustomUser securedLoadUserDetail(String username) {
return userRoleRepository.loadUserByUserName(username);
}

MethodInterceptor 主要包含一个 AuthorizationManager，
它现在将 “检查和返回最终决策的 AuthorizationDecision 对象” 的责任委托给适当的实现，
这里是 AuthenticatedAuthorizationManager。

AccessDecisionManager
GlobalMethodSecurityConfiguration 

https://springdoc.cn/spring-enablemethodsecurity-annotation/

GlobalMethodSecurityConfiguration 类已不再使用。Spring Security 使用分段配置和 AuthorizationManager 取代了它，
这意味着我们无需继承任何基础配置类就能定义我们的授权 Bean。
值得注意的是，AuthorizationManager 接口是泛型的，可以适应任何对象，尽管 Standard Security 适用于 MethodInvocation。
