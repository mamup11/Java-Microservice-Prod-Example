package com.companion.api.commons.profile.controller;

import com.google.common.base.Preconditions;
import com.companion.api.commons.profile.controller.resources.NewProfileDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class ProfileController {

    @PostMapping("/product")
    public ResponseEntity<?> saveProfile(@RequestBody NewProfileDto newProfileDto) {
        log.info("yeah its one");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) {
        Preconditions.checkArgument(productId != null, "The id provided must not be null");
        return ResponseEntity.ok().build();
    }

    int count = 0;
    @GetMapping("/products")
    public ResponseEntity<?> getProfile() {
        MDC.put("x-correlation-id", String.valueOf(count++));
        MDC.put("x-conversation-id", String.valueOf(count));
        log.trace("This is a trace message");
        log.debug("This is a debug message");
        log.info("This is an info message");
        log.warn("This is a warn message");
        IllegalArgumentException exception = new IllegalArgumentException("Hi, this is an error");
        log.error("This is an error message", exception);
        return ResponseEntity.ok().build();
    }
}
