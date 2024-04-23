package com.xiaosi.wx.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl  implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new User(username,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
//        return new User(username,"123456", Collections.EMPTY_LIST);
    }
}
