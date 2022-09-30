package com.mavuzer.authentication.users.payload;

import com.mavuzer.authentication.auth.provider.PassEncoder;
import com.mavuzer.authentication.users.dto.UserCreateDto;
import com.mavuzer.authentication.users.dto.UserDto;
import com.mavuzer.authentication.users.model.User;
import com.mavuzer.authentication.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PassEncoder
    private final PasswordEncoder passEncoder;


    @PostMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UserDto> createUser(@Valid @RequestBody UserCreateDto userDto){

        User user = User.builder().userName(userDto.getUserName())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .roles(Collections.emptySet())
                .email(userDto.getEmail())
                .password(passEncoder.encode(userDto.getPassword()))
                .build();

        return userService.save(user).flatMap(usr -> Mono.just(new UserDto(usr)));

    }



}
