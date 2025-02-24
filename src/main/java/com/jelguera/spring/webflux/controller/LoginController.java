package com.jelguera.spring.webflux.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jelguera.spring.webflux.config.TokenProvider;
import com.jelguera.spring.webflux.model.HttpResult;
import com.jelguera.spring.webflux.model.NewUserRequest;
import com.jelguera.spring.webflux.repository.UserDetailsRepository;
import com.jelguera.spring.webflux.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
 @CrossOrigin(origins = "*")
public class LoginController {

      private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    
    @Autowired
    private   UserService userService;
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

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public Mono<HttpResult> createUser(@RequestBody NewUserRequest newUserRequest) {
        return userService.createUser(newUserRequest).map(
                e -> {
                    HttpResult result = new HttpResult();
                    result.setCode(1);
                    result.setData(e);
                    result.setMsg("ok");
                    return result;
                }).onErrorResume(e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(new HttpResult(500, "error", e));

                });
    }
}

