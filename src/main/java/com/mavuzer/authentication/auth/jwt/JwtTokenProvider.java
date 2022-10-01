package com.mavuzer.authentication.auth.jwt;

import com.mavuzer.authentication.role.model.Role;
import com.mavuzer.authentication.users.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class JwtTokenProvider {

    private final String secretKey;
    private final long validityInMilliSeconds;
    private final SecretKey key;


    public JwtTokenProvider(@Value("${custom.jwt.secret-key}") String secretKey,
                            @Value("${custom.jwt.expiration}") long validityInMilliSeconds) {
        log.debug("Initialize JwtTokenProvider constructor");
        //this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.secretKey = secretKey;
        this.validityInMilliSeconds = validityInMilliSeconds;
        key = Keys.hmacShaKeyFor(this.secretKey.getBytes());
    }

    public String createToken(User user){

        log.debug("{}, createToken method invoked", this.getClass().getName());
        Claims claims = Jwts.claims().setSubject(user.getUserName());
        Set<String> roles = new HashSet<>();

        user.getRoles().forEach(role -> roles.add(role.getRoleName()));

        claims.put("roles",roles);
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validityInMilliSeconds);

        log.debug("{}, all claims are set!", this.getClass().getName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(key)
                .compact();
    }


    public boolean validateToken(String token){
        log.debug("Token validation is started!");
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();;
            return true;
        }catch (JwtException | IllegalArgumentException e){
            e.printStackTrace();
            log.error("Error occurred during token validation {}",e.getMessage());
            return false;
        }
    }

    public String getUserName(String token) {
        log.debug("Getting user name from token body!");
        return getTokenBody(token).getSubject();
    }

    public Set<Role> getRoles(String token) {
        log.debug("Getting roles from token body!");
        return getTokenBody(token).get("roles", Set.class);
    }


    public Claims getTokenBody(String token) {
        log.debug("Token parser is started!");
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }





}

