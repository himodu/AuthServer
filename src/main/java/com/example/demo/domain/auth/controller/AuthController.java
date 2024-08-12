package com.example.demo.domain.auth.controller;

import com.example.demo.domain.auth.model.LoginDto;
import com.example.demo.domain.auth.model.RegisterDto;
import com.example.demo.domain.auth.service.AuthService;
import com.example.demo.domain.auth.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterDto accountDto){
        authService.register(accountDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("login")
    public ResponseEntity<JwtToken> signIn(@RequestBody LoginDto loginDto){
        JwtToken token = authService.signIn(loginDto.getUserEmail(), loginDto.getPassword());
        return new ResponseEntity<JwtToken>(token,null, HttpStatus.OK);
    }

    @GetMapping("test")
    public String test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName() + "님 안녕하세요!";
    }
}
