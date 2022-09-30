package com.mavuzer.authentication.users.service;

import com.mavuzer.authentication.exception.UserNotFoundException;
import com.mavuzer.authentication.users.model.User;
import reactor.core.publisher.Mono;


public interface UserService {

    Mono<User> findByUserName(String userName);

    Mono<User> save(User user);

    Mono<User> deleteById(String id);

    Mono<User> update(String id, User user) throws UserNotFoundException;

    Mono<User> findById(String id);
}
