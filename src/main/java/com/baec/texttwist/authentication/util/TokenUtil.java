package com.baec.texttwist.authentication.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baec.texttwist.authentication.model.Role;
import com.baec.texttwist.authentication.model.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.stream.Collectors;

public class TokenUtil
{
    private static final String SECRET = "SECRET";
    private static final int ACCESS_TOKEN_EXPIRATION_MINUTES = 10;
    private static final int REFRESH_TOKEN_EXPIRATION_MINUTES = 60;

    public static String generateAccessToken(String issuer, User user)
    {
        Algorithm alg = Algorithm.HMAC256(SECRET.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_MINUTES * 60 * 1000))
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(alg);
    }

    public static String generateAccessToken(String issuer, AppUser user)
    {
        Algorithm alg = Algorithm.HMAC256(SECRET.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(issuer)
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(alg);
    }

    public static String generateRefreshToken(String issuer, User user)
    {
        Algorithm alg = Algorithm.HMAC256(SECRET.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_MINUTES * 60 * 1000))
                .withIssuer(issuer)
                .sign(alg);
    }

    public static DecodedJWT decodeJWT(String token)
    {
        Algorithm alg = Algorithm.HMAC256(SECRET.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        return verifier.verify(token);
    }
}
