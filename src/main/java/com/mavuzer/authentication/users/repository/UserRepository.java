package com.mavuzer.authentication.users.repository;

import com.mavuzer.authentication.users.model.User;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findById(String id);
    Mono<User> findByUserName(String userName);
    Mono<Boolean> existsById(String firstName);
    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsById(Publisher<String> id);
}
