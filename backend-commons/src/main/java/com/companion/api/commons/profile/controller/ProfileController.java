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

    @GetMapping("/product")
    public ResponseEntity<?> saveProfile(@RequestBody NewProfileDto newProfileDto) {
        log.info("yeah its one");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) {
        Preconditions.checkArgument(productId != null, "The id provided must not be null");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProfile() {
        log.trace("This is a trace message");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
}
