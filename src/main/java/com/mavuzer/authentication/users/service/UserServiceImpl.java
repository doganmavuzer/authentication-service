package com.mavuzer.authentication.users.service;

import com.mavuzer.authentication.exception.UserNotFoundException;
import com.mavuzer.authentication.users.model.User;
import com.mavuzer.authentication.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

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
    public Mono<User> deleteById(String id){

        log.debug("Deleting the record - id : {}",id);

        return userRepository.findById(id)
                .flatMap(user -> userRepository.deleteById(id)
                        .then(Mono.just(user)));

    }

    @Override
    public Mono<User> update(String id, User user) throws UserNotFoundException {

        log.debug("Updating user - id: {} , userName: {}",id, user.getUserName());
        return userRepository.findById(id).flatMap(oldUser -> {
            oldUser.setUserName(user.getUserName());
            oldUser.setFirstName(user.getFirstName());
            oldUser.setUserName(user.getLastName());
            oldUser.setEmail(user.getEmail());
            oldUser.setRoles(user.getRoles());
            oldUser.setAccountNonExpired(user.isAccountNonExpired());
            oldUser.setEnabled(user.isEnabled());
            return userRepository.save(oldUser);
        });
    }

    @Override
    public Mono<User> findById(String id) {

        return userRepository.findById(id);
    }

}
