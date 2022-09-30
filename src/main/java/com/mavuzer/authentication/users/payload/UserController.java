package com.mavuzer.authentication.users.payload;

import com.mavuzer.authentication.auth.provider.PassEncoder;
import com.mavuzer.authentication.users.dto.UserCreateDto;
import com.mavuzer.authentication.users.dto.UserDto;
import com.mavuzer.authentication.users.dto.UserUpdateDto;
import com.mavuzer.authentication.users.model.User;
import com.mavuzer.authentication.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    @PassEncoder
    private final PasswordEncoder passEncoder;


    @PostMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<UserDto>> createUser(@Valid @RequestBody UserCreateDto userDto){

        User user = User.builder().userName(userDto.getUserName())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .roles(Collections.emptySet())
                .email(userDto.getEmail())
                .password(passEncoder.encode(userDto.getPassword()))
                .build();

        return ResponseEntity.ok(userService.save(user).publishOn(Schedulers.boundedElastic())
                .map(UserDto::new));
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<UserDto>> updateUser(@PathVariable(value = "id") String id,@RequestBody UserUpdateDto userDto){

        return ResponseEntity.ok(userService.update(id,userDto).map(UserDto::new));
    }

    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> deleteById(@PathVariable(value = "id") String id){

        return userService.deleteById(id);
    }

    @GetMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<UserDto>> getAllUsers(){

        return ResponseEntity.ok(userService.findAll().delayElements(Duration.ofSeconds(1)).map(UserDto::new));
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<UserDto>> getUserById(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(userService.findById(id).map(UserDto::new));
    }



}
