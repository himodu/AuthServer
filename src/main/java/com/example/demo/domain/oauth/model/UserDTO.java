package com.example.demo.domain.oauth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UserDTO {
    private String role;
    private String name;
    private String username;
}
