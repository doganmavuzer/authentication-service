package com.mavuzer.authentication.role.service;

import com.mavuzer.authentication.role.dto.RoleDto;
import com.mavuzer.authentication.role.model.Role;
import com.mavuzer.authentication.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    @Override
    public Mono<Role> findRoleById(String id) {
        return roleRepository.findById(id);
    }

    @Override
    public Mono<Role> findRoleByName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public Flux<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Mono<Role> updateRole(String id, RoleDto roleDto) {
        log.debug("Updating role - id: {}",id);

        return roleRepository.findById(id).flatMap(oldRole -> {
            oldRole.setRoleName(oldRole.getRoleName() != null ? oldRole.getRoleName() : roleDto.getRoleName());
            return roleRepository.save(oldRole);
        });
    }

    @Override
    public Mono<ServerResponse> deleteById(String id) {
        return roleRepository.deleteById(id).then(ServerResponse.ok().build());
    }

    @Override
    public Mono<Role> save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Mono<Role> update(String id, RoleDto role) {
        return roleRepository.findById(id).flatMap(rl -> {
            rl.setRoleName(role.getRoleName() != null ? role.getRoleName() : rl.getRoleName());
            return roleRepository.save(rl);
        });
    }
}
