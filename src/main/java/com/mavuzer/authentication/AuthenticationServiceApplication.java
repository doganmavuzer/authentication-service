package com.mavuzer.authentication;

import com.mavuzer.authentication.users.model.User;
import com.mavuzer.authentication.users.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Optional;

@SpringBootApplication()
@ComponentScan(value = "com.mavuzer")
@Slf4j
public class AuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        return args -> {
            User user = User.builder().userName("test_user")
                    .firstName("TEST")
                    .lastName("API Test")
                    .password(passwordEncoder.encode("password"))
                    .email("test@gmail.com")
                    .roles(Collections.EMPTY_SET)
                    .build();
            Optional<User> existUser =Optional.ofNullable(userService.findByUserName(user.getUserName()).block());

            if (existUser.isEmpty()){
                Mono<User> userMono = userService.save(user);
                log.debug("User Mono {}", userMono);
                userMono.subscribe();
            }

        };
    }


}
