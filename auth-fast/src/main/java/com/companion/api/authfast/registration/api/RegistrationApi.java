package com.companion.api.authfast.registration.api;

import com.companion.api.authfast.hashing.PasswordHashingApi;
import com.companion.api.authfast.registration.controller.dto.RegistrationDto;
import com.companion.api.authfast.repository.UserModelRepository;
import com.companion.api.authfast.repository.model.UserModel;
import com.companion.api.commons.error.model.exceptions.IllegalArgumentWithCodeException;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegistrationApi {

    private final PasswordHashingApi passwordHashingApi;
    private final UserModelRepository userModelRepository;

    @Autowired
    public RegistrationApi(PasswordHashingApi passwordHashingApi, UserModelRepository userModelRepository) {
        this.passwordHashingApi = passwordHashingApi;
        this.userModelRepository = userModelRepository;
    }

    public void register(RegistrationDto registrationDto) {
        Preconditions.checkArgument(StringUtils.isNotBlank(registrationDto.getUsername()),
                "Registration information should be provided");
        Preconditions.checkArgument(StringUtils.isNotBlank(registrationDto.getPassword()),
                "Registration information should be provided");

        UserModel userModel = UserModel.builder()
                .username(registrationDto.getUsername())
                .password(passwordHashingApi.hashPassword(registrationDto.getPassword()))
                .build();

        saveNewUser(userModel);
    }

    private void saveNewUser(UserModel userModel) {
        try {
            userModelRepository.save(userModel);
        } catch (DataIntegrityViolationException dive) {
            if (StringUtils.isNotBlank(dive.getMessage()) && "USERNAME_UNIQUE_CONSTRAINT".contains(dive.getMessage())) {
                throw new IllegalArgumentWithCodeException("Username already taken", "USERNAME_TAKEN");
            }

            // At this point the error is possibly a collision of the UUID V4. Try to save again and if an exception is
            // throw again it will pop up to the response
            userModelRepository.save(userModel);
        }
        log.info("User with ID: {} was saved successfully", userModel.getUserId());
    }
}
