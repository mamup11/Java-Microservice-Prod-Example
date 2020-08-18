package com.mamup.example.commons.profile.controller.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewProfileDto {
    private String username;
    private String difficulty;
    private Integer championId;
}
