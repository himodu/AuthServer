package com.example.demo.global.infrastructure;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String userEmail;
    private String password;
    private String refreshToken;

    private Role role;

    @Builder
    public AccountEntity(String username, String userEmail, String password, String refreshToken, Role role) {
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;
        this.refreshToken = refreshToken;
        this.role = role;
    }
}
