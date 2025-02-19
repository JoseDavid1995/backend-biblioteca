package com.jelguera.spring.webflux.config;



import org.springframework.security.core.AuthenticationException;


public class JwtAuthenticationException extends AuthenticationException {
    JwtAuthenticationException(String msg) {
        super(msg);
    }
}
