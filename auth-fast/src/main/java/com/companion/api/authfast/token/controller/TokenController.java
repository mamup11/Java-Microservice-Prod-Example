package com.companion.api.authfast.token.controller;

import com.companion.api.commons.error.model.exceptions.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/token")
public class TokenController {


    @PostMapping("/validate")
    public ResponseEntity<?> validate() {

        if (true)
        throw new UnauthorizedException("Some random error");
        return ResponseEntity.ok().build();
    }
}
