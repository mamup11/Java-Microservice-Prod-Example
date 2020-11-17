package com.companion.api.authfast.authorization.controller;

import com.companion.api.authfast.authorization.api.TokenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/token")
public class TokenController {

    private final TokenApi tokenApi;

    @Autowired
    public TokenController(TokenApi tokenApi) {
        this.tokenApi = tokenApi;
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validate(@RequestHeader("Authorization") String token) {
        tokenApi.validateToken(token);
        return ResponseEntity.ok().build();
    }
}
