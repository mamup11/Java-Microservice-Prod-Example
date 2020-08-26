package com.companion.api.commons.profile.controller;

import com.google.common.base.Preconditions;
import com.companion.api.commons.profile.controller.resources.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class ProfileController {

    @PostMapping("/product")
    public ResponseEntity<?> saveProfile(@RequestBody Test test) {
        log.info("Hello {}", test.getUsername());
        Test response = new Test();
        response.setUsername("Hello " + test.getUsername() + " This is a test!");
        response.setPassword(test.getPassword());
        response.setInnerTest(test.getInnerTest());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) {
        Preconditions.checkArgument(productId != null, "The id provided must not be null");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProfile() {
        log.info("This is a info message");
        return ResponseEntity.ok().build();
    }
}
