package com.jelguera.spring.webflux.service;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jelguera.spring.webflux.entity.MyUserDetails;
import com.jelguera.spring.webflux.model.NewUserRequest;

import reactor.core.publisher.Mono;

@Service
public class UserService {
    

    @Autowired
    private com.jelguera.spring.webflux.repository.UserDetailsRepository UserDetailsRepository;
    private final PasswordEncoder passwordEncoder; 

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<MyUserDetails> createUser(NewUserRequest newUserRequest) {
        MyUserDetails c = new MyUserDetails();
        c.setUsername(newUserRequest.getUsuario());
        c.setPassword(passwordEncoder.encode(newUserRequest.getContrasenia()));
        c.setFechaCreacion(Timestamp.from(Instant.now()));
        c.setEstado("A");
        c.setAuthorities("ADMIN");
        return  UserDetailsRepository.save(c)
        .doOnError(error -> {
            System.err.println("Error saving create book: " + error.getMessage());
        });
            

    }
}
    
