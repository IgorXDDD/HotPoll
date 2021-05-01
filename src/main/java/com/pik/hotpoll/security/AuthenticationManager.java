package com.pik.hotpoll.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String pw       = authentication.getCredentials().toString();

        return new UsernamePasswordAuthenticationToken(username, pw, authentication.getAuthorities());

    }
}
