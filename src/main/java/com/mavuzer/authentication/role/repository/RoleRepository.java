package com.mavuzer.authentication.role.repository;

import com.mavuzer.authentication.role.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByRoleName(String roleName);
}
