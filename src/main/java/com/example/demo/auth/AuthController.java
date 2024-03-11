package com.example.demo.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public void register(RegisterDto accountDto){
        authService.register(accountDto);
    }

    @PostMapping("login")
    public JwtToken signIn(LoginDto loginDto){
        return authService.signIn(loginDto.getUseremail(), loginDto.getPassword());
    }




}
