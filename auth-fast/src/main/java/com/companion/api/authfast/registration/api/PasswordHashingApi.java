package com.companion.api.authfast.registration.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class PasswordHashingApi {

    private static final Argon2PasswordEncoder ARGON_2_PASSWORD_ENCODER =
            new Argon2PasswordEncoder(16, 32, 1, 65536, 10);

    public String hashPassword(String password) {
        Instant start = Instant.now();

        // Generate password's hash with the Argon2id algorithm
        String hash = ARGON_2_PASSWORD_ENCODER.encode(password);

        if (log.isDebugEnabled()) {
            log.debug("Generated Hash: {}", hash);
            log.debug("Hashing took {} ms", ChronoUnit.MILLIS.between(start, Instant.now()));
        }

        return hash;
    }

}
