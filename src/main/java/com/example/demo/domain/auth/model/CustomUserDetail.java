package com.example.demo.domain.auth.model;

import com.example.demo.global.infrastructure.AccountEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
@RequiredArgsConstructor
public class CustomUserDetail implements UserDetails {
    private final AccountEntity account;
    @Override
    public String getPassword(){
        return account.getPassword();
    }
    @Override
    public String getUsername(){
        return account.getUserEmail();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+account.getRole().toString()));
        return authorities;
    }
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    public boolean isEnabled(){
        return true;
    }
}
