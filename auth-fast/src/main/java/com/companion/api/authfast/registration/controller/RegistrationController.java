package com.companion.api.authfast.registration.controller;

import com.companion.api.authfast.authentication.model.TokenResponseModel;
import com.companion.api.authfast.registration.api.RegistrationApi;
import com.companion.api.authfast.registration.controller.dto.RegistrationDto;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class RegistrationController {
    private final RegistrationApi registrationApi;

    @Autowired
    public RegistrationController(RegistrationApi registrationApi) {
        this.registrationApi = registrationApi;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegistrationDto registrationDto) {
        Preconditions.checkArgument(registrationDto != null,
                "Registration information should be provided");

        registrationApi.register(registrationDto);
        return ResponseEntity.accepted().build();
    }
}
