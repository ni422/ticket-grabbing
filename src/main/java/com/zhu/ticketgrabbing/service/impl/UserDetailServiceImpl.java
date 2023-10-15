package com.zhu.ticketgrabbing.service.impl;

import com.zhu.ticketgrabbing.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 根据用户名获取用户信息，并把信息交给Security保存
 */
//@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        return new User("zhu", "{noop}123456");
    }
}
