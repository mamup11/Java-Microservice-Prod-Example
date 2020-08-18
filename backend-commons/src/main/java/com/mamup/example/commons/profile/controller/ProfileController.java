package com.mamup.example.commons.profile.controller;

import com.google.common.base.Preconditions;
import com.mamup.example.commons.profile.controller.resources.NewProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProfileController {

    @PostMapping("/profile")
    public ResponseEntity<?> saveProfile(@RequestBody NewProfileDto newProfileDto) {
        Preconditions.checkArgument(newProfileDto != null, "The body must not be null");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<?> getProfile(@PathVariable("profileId") Long profileId) {
        Preconditions.checkArgument(profileId != null, "The id provided must not be null");
        return ResponseEntity.ok().build();
    }
}
