package com.mavuzer.authentication.users.service;

import com.mavuzer.authentication.exception.UserNotFoundException;
import com.mavuzer.authentication.users.dto.UserUpdateDto;
import com.mavuzer.authentication.users.model.User;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UserService {

    Mono<User> findByUserName(String userName);

    Mono<User> save(User user);

    Mono<ServerResponse> deleteById(String id);

    Mono<User> update(String id, UserUpdateDto updateDto) throws UserNotFoundException;

    Mono<User> findById(String id);

    Flux<User> findAll();
}
