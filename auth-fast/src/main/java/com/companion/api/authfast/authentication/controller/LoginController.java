package com.companion.api.authfast.authentication.controller;

import com.companion.api.authfast.authentication.api.LoginApi;
import com.companion.api.authfast.authentication.controller.dto.LoginDto;
import com.companion.api.authfast.authentication.controller.dto.LoginResponseDto;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

    private final LoginApi loginApi;

    @Autowired
    public LoginController(LoginApi loginApi) {
        this.loginApi = loginApi;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
        Preconditions.checkArgument(loginDto != null, "Login information must be present");
        return ResponseEntity.ok(loginApi.login(loginDto.getUsername(), loginDto.getPassword()));
    }
}
