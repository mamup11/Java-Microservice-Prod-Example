package com.companion.api.authfast.hashing;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
            log.debug("Hashing took {} ms", ChronoUnit.MILLIS.between(start, Instant.now()));
        }

        return hash;
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        Preconditions.checkArgument(StringUtils.isNotBlank(rawPassword), "Password cannot be null");
        Preconditions.checkArgument(StringUtils.isNotBlank(encodedPassword), "Hash cannot be null");

        return ARGON_2_PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }

}
