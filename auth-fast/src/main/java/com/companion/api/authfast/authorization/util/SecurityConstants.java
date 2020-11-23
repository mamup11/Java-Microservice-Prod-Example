package com.companion.api.authfast.authorization.util;

import com.companion.api.commons.error.model.exceptions.UnauthorizedException;

public class SecurityConstants {

    private SecurityConstants() {
    }

    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * This method throws a general Unauthorized Exception with general message
     */
    public static void throwGeneralUnauthorizedException() {
        throw new UnauthorizedException("Unauthorized");
    }
}
