package com.monitor.transaction.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.monitor.transaction.model.entity.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${app.monitor.jwt.jwt-secret}")
    private String jwtSecret;
    @Value("${app.monitor.jwt.app-name}")
    private String appName;
    @Value("${app.monitor.jwt.jwt-expired}")
    private Long expired;

    public String generateToken(AppUser appUser){
        try {

            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));

            String token = JWT.create()
                    .withIssuer(appName) // info untuk application name yang kita buat
                    .withSubject(appUser.getId()) //menentukan object yang akan dibuat, biasanya dari ID
                    .withExpiresAt(Instant.now().plusSeconds(expired))
                    .withIssuedAt(Instant.now()) //menetapkan waktu token kapan dibuat
                    .withClaim("role", appUser.getRole().name())
                    .sign(algorithm); //untuk ttd kontrak / penetapannya bahwa algoritm yang sudah kita set diatas akan dipakai
            return token;
        }catch (JWTCreationException e){
            throw new RuntimeException();
        }
    }

    public boolean verifyJwtToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        }catch (JWTVerificationException e){
            throw new RuntimeException();
        }
    }

    public Map<String,String> getUserInfoByToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role").asString());
            return userInfo;
        }catch (JWTVerificationException e){
            throw new RuntimeException();
        }
    }


}
