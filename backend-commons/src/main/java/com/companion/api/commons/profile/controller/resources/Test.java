package com.companion.api.commons.profile.controller.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Test {
    private String username;
    private String password;
    private String id;
    private InnerTest innerTest;
}
