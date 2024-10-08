package com.example.demo.domain.auth.service;

import com.example.demo.global.infrastructure.AccountEntity;
import com.example.demo.global.infrastructure.AccountRepository;
import com.example.demo.domain.auth.model.CustomUserDetail;



import com.example.demo.global.exception.AccountNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Optional<AccountEntity> account = accountRepository.findByUserEmail(userEmail);

        if(account.isEmpty()) {
            throw new AccountNotExistException("사용자 없음");
        }else{
            return new CustomUserDetail(account.get());
        }
    }
}
