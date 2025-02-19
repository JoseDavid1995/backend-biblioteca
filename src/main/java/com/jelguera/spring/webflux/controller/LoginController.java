package com.jelguera.spring.webflux.controller;

import java.util.Map;
import java.util.Scanner;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jelguera.spring.webflux.config.TokenProvider;
import com.jelguera.spring.webflux.repository.UserDetailsRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
 @CrossOrigin(origins = "*")
public class LoginController {
    
    private final PasswordEncoder passwordEncoder; 
    private final TokenProvider tokenProvider;
  private final UserDetailsRepository myUserRepository;
    
    @PostMapping("/login")
    Mono<LoginResponse> login(@RequestBody Map<String, String> user) { 
        System.out.println("Token: " + passwordEncoder.encode("1234"));
        return myUserRepository.findByUsername(user.get("username"))
                .doOnNext(u -> System.out.println("Usuario encontrado: " + u))
                .filter(u -> passwordEncoder.matches(user.get("password"), u.getPassword()))
                .map(tokenProvider::generateToken)
                .map(LoginResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
    }
 
}

