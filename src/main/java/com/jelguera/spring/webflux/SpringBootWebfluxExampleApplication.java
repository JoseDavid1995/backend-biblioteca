package com.jelguera.spring.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication(scanBasePackages = "com.jelguera.spring.webflux")
@EnableFeignClients(basePackages = "com.jelguera.spring.webflux.client") // Adjust if necessary
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableR2dbcRepositories
public class SpringBootWebfluxExampleApplication {


  public static void main(String[] args) {
    SpringApplication.run(SpringBootWebfluxExampleApplication.class, args);
  }

}
