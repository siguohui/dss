package com.xiaosi.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef="jpaAuditConfig")
public class JpaAuditConfig implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

//        UserDetails user;
//        try {
//            user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            return Optional.ofNullable(user.getUsername());
//        }catch (Exception e){
//            return Optional.empty();
//        }

        /*SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userId = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userId = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userId = (String) authentication.getPrincipal();
            }
        }
        return userId;*/

        return Optional.of(1L);
    }
}
