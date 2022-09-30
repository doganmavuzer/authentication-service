package com.mavuzer.authentication.role.service;

import com.mavuzer.authentication.role.dto.RoleDto;
import com.mavuzer.authentication.role.model.Role;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService {

    Mono<Role> findRoleById(String id);

    Mono<Role> findRoleByName(String roleName);

    Flux<Role> findAll();

    Mono<Role> updateRole(String id, RoleDto role);

    Mono<ServerResponse> deleteById(String id);

    Mono<Role> save(Role role);

    Mono<Role> update(String id, RoleDto role);


}
