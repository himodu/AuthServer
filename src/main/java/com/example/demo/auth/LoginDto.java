package com.example.demo.auth;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class LoginDto {
    private String useremail;
    private String password;
}
