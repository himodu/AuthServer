package com.example.demo.domain.auth.model;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String username;
    private String userEmail;
    private String password;
}
