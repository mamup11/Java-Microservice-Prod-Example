package com.companion.api.authfast.authorization.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.companion.api.authfast.authorization.util.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.companion.api.authfast.authorization.util.SecurityConstants.TOKEN_PREFIX;

@Slf4j
@Component
public class TokenApi {

    private final long ttl;
    private final JWTVerifier jwtVerifier;
    private final Algorithm signingAlgorithm;

    @Autowired
    public TokenApi(@Value("${authorization.jwt.hash.secret}") String secret,
                    @Value("${authorization.jwt.ttl}") long ttl) {
        this.ttl = ttl;
        this.signingAlgorithm = HMAC512(secret.getBytes());
        this.jwtVerifier = JWT.require(signingAlgorithm)
                .build();
    }

    /**
     * This method verifies an string token with or without the token prefix "Bearer "
     *
     * @param token the token to be validated
     */
    public void validateToken(String token) {
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            SecurityConstants.throwGeneralUnauthorizedException();
        }

        try {
            jwtVerifier.verify(token.replace(TOKEN_PREFIX, ""));
        } catch (JWTVerificationException e) {
            log.debug("Token validation failed for token: {}", token, e);
            SecurityConstants.throwGeneralUnauthorizedException();
        }
    }

    /**
     * This method generates a token with expiration of 2 hours.
     *
     * @return A valid token with 2 hours to expire
     */
    public String generateToken(String userID) {
        return JWT.create()
                .withClaim("userId", userID)
                // TODO: add roles
                .withExpiresAt(new Date(System.currentTimeMillis() + ttl))
                .sign(signingAlgorithm);
    }
}
