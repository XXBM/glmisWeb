package com.glmis.config;

import com.glmis.security.CustomUserDetails;
import com.glmis.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by xuling on 2016/10/11.
 * 提供登录验证处理逻辑
 */


@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userService;

    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        //password = Utils.makeMD5(password);
        CustomUserDetails user = (CustomUserDetails) userService.loadUserByUsername(username);

        if (user == null) {
            throw new BadCredentialsException(" 用户不存在");
        }

        //加密过程在这里体现
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}

