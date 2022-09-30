package com.mavuzer.authentication.role.repository;

import com.mavuzer.authentication.role.model.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface RoleRepository extends ReactiveMongoRepository<Role,String> {
    Mono<Role> findRoleByRoleName(String roleName);
}
