package com.mavuzer.authentication.auth.payload;

import com.mavuzer.authentication.auth.dto.AuthenticationResponse;
import com.mavuzer.authentication.auth.dto.LoginRequest;
import com.mavuzer.authentication.auth.jwt.JwtTokenProvider;
import com.mavuzer.authentication.auth.provider.PassEncoder;
import com.mavuzer.authentication.exception.UserAlreadyExist;
import com.mavuzer.authentication.exception.UserNotFoundException;
import com.mavuzer.authentication.role.model.Role;
import com.mavuzer.authentication.users.dto.UserCreateDto;
import com.mavuzer.authentication.users.dto.UserDto;
import com.mavuzer.authentication.users.model.User;
import com.mavuzer.authentication.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @PassEncoder
    private final PasswordEncoder passEncoder;

    private final UserServiceImpl userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/sign-up")
    public Mono<ResponseEntity<UserDto>> register(@Valid @RequestBody UserCreateDto userDto){

        return userService.findByUserName(userDto.getUserName())
                .publishOn(Schedulers.boundedElastic())
                .map(usr -> {

                    Mono<User> user = userService.save(User.builder()
                        .userName(usr.getUserName())
                        .firstName(usr.getFirstName())
                        .lastName(usr.getLastName())
                        .email(usr.getEmail())
                        .roles(usr.getRoles().stream().map(role -> new Role(role.getRoleName())).collect(Collectors.toSet()))
                        .password(passEncoder.encode(usr.getPassword())).build());
                    return ResponseEntity.ok(new UserDto(Objects.requireNonNull(user.block())));
                })
                .switchIfEmpty(Mono.error(new UserAlreadyExist("User already exist")));

    }

    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AuthenticationResponse>> login(@Valid @RequestBody LoginRequest loginRequest){

        return userService.findByUserName(loginRequest.getUserName())
                .filter(userDetails -> passEncoder.matches(loginRequest.getPassword(),userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthenticationResponse(jwtTokenProvider.createToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

}
