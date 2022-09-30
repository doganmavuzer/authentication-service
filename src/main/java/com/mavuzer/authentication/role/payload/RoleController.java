package com.mavuzer.authentication.role.payload;

import com.mavuzer.authentication.role.dto.RoleDto;
import com.mavuzer.authentication.role.model.Role;
import com.mavuzer.authentication.role.service.RoleServiceImpl;
import com.mavuzer.authentication.users.dto.UserDto;
import com.mavuzer.authentication.users.dto.UserUpdateDto;
import com.mavuzer.authentication.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;

@RestController
@RequestMapping("/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl roleService;


    @PostMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Role>> createRole(@Valid @RequestBody RoleDto roleDto){

        Role role = Role.builder().roleName(roleDto.getRoleName())
                .build();

        return ResponseEntity.ok(roleService.save(role));
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Role>> updateRole(@PathVariable(value = "id") String id,@RequestBody RoleDto roleDto){

        return ResponseEntity.ok(roleService.update(id,roleDto));
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> deleteById(@PathVariable(value = "id") String id){

        return roleService.deleteById(id);
    }

    @GetMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<Role>> getAllUsers(){

        return ResponseEntity.ok(roleService.findAll().delayElements(Duration.ofSeconds(1)));
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Role>> getUserById(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(roleService.findRoleById(id));
    }



}
