package com.kgcorner.springsecuritydemo.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

public final class JWTUtility {

    private JWTUtility(){}

    public static String createJWTToken(String payload) {
        Instant now = Instant.now();
        try {
            Algorithm algorithm = Algorithm.HMAC256(Constants.JWT_SECRET);
            return JWT.create()
                    .withClaim("user", payload)
                    .withIssuedAt(Date.from(now))
                    .withIssuer(Constants.ISSUER)
                    .withExpiresAt(Date.from(now.plusSeconds(Constants.TOKEN_LIFE_SPAN_IN_SECOND)))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(Constants.JWT_SECRET);
            JWTVerifier veryfier = JWT.require(algorithm).withIssuer(Constants.ISSUER).build();
            DecodedJWT jwt = veryfier.verify(token);
            return jwt.getClaim("user").asString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (JWTVerificationException x) {
            System.out.println("JWT veryfication failed");
        }
        return null;
    }
}
