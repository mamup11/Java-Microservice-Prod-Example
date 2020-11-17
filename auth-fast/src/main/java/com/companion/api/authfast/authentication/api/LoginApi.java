package com.companion.api.authfast.authentication.api;

import com.auth0.jwt.JWT;
import com.companion.api.authfast.authentication.model.TokenResponseModel;
import com.companion.api.commons.error.model.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Slf4j
@Component
public class LoginApi {

    private static final String MOCK_USERNAME = "username";
    private static final String MOCK_PASSWORD = "password";

    private final byte[] hashSecret;

    @Autowired
    public LoginApi(@Value("${authorization.jwt.hash.secret}") String secret) {
        this.hashSecret = secret.getBytes();
    }

    /**
     * This method check the provided credentials and returns a valid token with expiration of 2 hours if successful.
     * <p>
     * This is a simple mock version, a real authentication
     * implementation is planned on being developed as part of this framework in future releases, for now this is only
     * a mock version and should never be used as it is
     *
     * @param username
     * @param password
     * @return
     */
    public TokenResponseModel login(String username, String password) {
        if (MOCK_USERNAME.equals(username) && MOCK_PASSWORD.equals(password)) {
            return TokenResponseModel.builder()
                    .accessToken(generateToken())
                    .build();
        }
        throw new UnauthorizedException("Unauthorized");
    }

    /**
     * This method generates a token with expiration of 2 hours.
     * <p>
     * This is a simple mock version, a real authentication
     * implementation is planned on being developed as part of this framework in future releases, for now this is only
     * a mock version and should never be used as it is
     *
     * @return A valid token with 2 hours to expire
     */
    private String generateToken() {
        return JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 7200000))
                .sign(HMAC512(hashSecret));
    }
}
