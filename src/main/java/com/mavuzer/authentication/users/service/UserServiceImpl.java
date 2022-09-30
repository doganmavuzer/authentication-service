package com.mavuzer.authentication.users.service;

import com.mavuzer.authentication.auth.provider.PassEncoder;
import com.mavuzer.authentication.exception.UserNotFoundException;
import com.mavuzer.authentication.role.repository.RoleRepository;
import com.mavuzer.authentication.users.dto.UserUpdateDto;
import com.mavuzer.authentication.users.model.User;
import com.mavuzer.authentication.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    @PassEncoder
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Mono<User> findByUserName(String userName) {
        log.debug("Finding user by {} ",userName);
        return userRepository.findByUserName(userName);
    }

    @Override
    public Mono<User> save(User user) {
        log.debug("Recording user object to database!");
        return userRepository.save(user);
    }

    @Override
    public Mono<ServerResponse> deleteById(String id){

        log.debug("Deleting the record - id : {}",id);

        return userRepository.deleteById(id).then(ServerResponse.ok().build());

    }

    @Override
    public Mono<User> update(String id, UserUpdateDto updateDto) throws UserNotFoundException {

        log.debug("Updating user - id: {}",id);

        return userRepository.findById(id).flatMap(oldUser -> {
            oldUser.setUserName(oldUser.getUserName() != null ? oldUser.getUserName() : updateDto.getUserName());
            oldUser.setFirstName(oldUser.getFirstName() != null ? oldUser.getFirstName() : updateDto.getFirstName());
            oldUser.setUserName(oldUser.getLastName() != null ? oldUser.getLastName() : updateDto.getLastName());
            oldUser.setEmail(oldUser.getEmail() != null ? oldUser.getEmail() : updateDto.getEmail());
            oldUser.setPassword(passwordEncoder.matches(updateDto.getPassword(),oldUser.getPassword()) ? oldUser.getPassword() : passwordEncoder.encode(updateDto.getPassword())  );
            return userRepository.save(oldUser);
        });
    }

    @Override
    public Mono<User> findById(String id) {

        return userRepository.findById(id);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

}
