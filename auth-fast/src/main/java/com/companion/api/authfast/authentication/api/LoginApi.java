package com.companion.api.authfast.authentication.api;

import com.companion.api.authfast.authentication.controller.dto.LoginResponseDto;
import com.companion.api.authfast.authorization.api.TokenApi;
import com.companion.api.authfast.hashing.PasswordHashingApi;
import com.companion.api.authfast.repository.UserModelRepository;
import com.companion.api.authfast.repository.model.UserModel;
import com.companion.api.commons.error.model.exceptions.UnauthorizedException;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class LoginApi {

    private final UserModelRepository userModelRepository;
    private final TokenApi tokenApi;
    private final PasswordHashingApi passwordHashingApi;

    public LoginApi(UserModelRepository userModelRepository, TokenApi tokenApi, PasswordHashingApi passwordHashingApi) {
        this.userModelRepository = userModelRepository;
        this.tokenApi = tokenApi;
        this.passwordHashingApi = passwordHashingApi;
    }

    /**
     * This method check the provided credentials and returns a valid token with expiration of 2 hours if successful.
     *
     * @param username Username as String
     * @param password Password as String
     * @return Access token response
     */
    public LoginResponseDto login(String username, String password) {
        Preconditions.checkArgument(StringUtils.isNotBlank(username), "Username must be provided");
        Preconditions.checkArgument(StringUtils.isNotBlank(password), "Password must be provided");

        Optional<UserModel> userModelOpt = userModelRepository.findByUsername(username);

        if (userModelOpt.isPresent() && passwordHashingApi.checkPassword(password, userModelOpt.get().getPassword())) {
            return LoginResponseDto.builder()
                    .accessToken(tokenApi.generateToken(userModelOpt.get().getUserId()))
                    .build();
        }

        throw new UnauthorizedException("Unauthorized");
    }
}
