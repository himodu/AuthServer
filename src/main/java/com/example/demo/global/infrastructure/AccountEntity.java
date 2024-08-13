package com.example.demo.global.infrastructure;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "Account")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String userEmail;
    private String password;
    private String refreshToken;

    private Role role;

    @Builder
    public AccountEntity(String username, String name, String userEmail, String password, String refreshToken, Role role) {
        this.username = username;
        this.name = name;
        this.userEmail = userEmail;
        this.password = password;
        this.refreshToken = refreshToken;
        this.role = role;
    }
}
