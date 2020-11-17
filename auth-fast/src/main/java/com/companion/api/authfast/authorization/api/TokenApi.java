package com.companion.api.authfast.authorization.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.companion.api.commons.error.model.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.companion.api.authfast.authorization.SecurityConstants.TOKEN_PREFIX;

@Slf4j
@Component
public class TokenApi {

    private final JWTVerifier jwtVerifier;

    @Autowired
    public TokenApi(@Value("${authorization.jwt.hash.secret}") String secret) {
        this.jwtVerifier = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build();
    }

    /**
     * This method verifies an string token with or without the token prefix "Bearer "
     *
     * @param token the token to be validated
     */
    public void validateToken(String token) {
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            throwGeneralUnauthorizedException();
        }

        try {
            jwtVerifier.verify(token.replace(TOKEN_PREFIX, ""));
        } catch (JWTVerificationException e) {
            log.debug("Token validation failed for token: {}", token, e);
            throwGeneralUnauthorizedException();
        }
    }

    /**
     * This method throws a general Unauthorized Exception with general message
     */
    private void throwGeneralUnauthorizedException() {
        throw new UnauthorizedException("Unauthorized");
    }
}
