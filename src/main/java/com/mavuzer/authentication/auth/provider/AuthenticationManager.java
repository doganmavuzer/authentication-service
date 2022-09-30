package com.mavuzer.authentication.auth.provider;

import com.mavuzer.authentication.auth.jwt.JwtTokenProvider;
import com.mavuzer.authentication.role.model.Role;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        String authToken = authentication.getCredentials().toString();
        String username = jwtTokenProvider.getUserName(authToken);

        return Mono.just(jwtTokenProvider.validateToken(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtTokenProvider.getTokenBody(authToken);
                    Set<Role> rolesMap = claims.get("roles",  Set.class);
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            rolesMap.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList())
                    );
                });
    }
}
