package com.example.demo.auth;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
public class RegisterDto {
    private String username;
    private String userEmail;
    private String password;
}
