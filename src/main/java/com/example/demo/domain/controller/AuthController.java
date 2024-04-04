package com.example.demo.domain.controller;

import com.example.demo.domain.model.LoginDto;
import com.example.demo.domain.model.RegisterDto;
import com.example.demo.domain.model.ResponseDto;
import com.example.demo.domain.service.AuthService;
import com.example.demo.global.auth.jwt.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterDto accountDto){
        int id = authService.register(accountDto);
        ResponseDto responseDto = new ResponseDto(201, "회원가입이 성공적으로 처리되었습니다.");
        return ResponseEntity.created(URI.create("/account/"+id)).body(responseDto);
    }
    @PostMapping("login")
    public ResponseEntity<JwtToken> signIn(@RequestBody LoginDto loginDto){
        JwtToken token = authService.signIn(loginDto.getUserEmail(), loginDto.getPassword());
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("test")
    public ResponseEntity<ResponseDto> test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ResponseDto responseDto = new ResponseDto(200, authentication.getName()+"님 안녕하세요!");
        return ResponseEntity.ok().body(responseDto);
    }
}
