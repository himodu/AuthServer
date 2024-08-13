package com.example.demo.domain.test;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v0")
public class AdminController {

    @GetMapping("test")
    public String test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName() + "관리자 님 안녕하세요!";
    }
}
