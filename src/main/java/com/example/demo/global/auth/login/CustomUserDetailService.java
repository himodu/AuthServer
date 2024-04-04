package com.example.demo.global.auth.login;

import com.example.demo.domain.infrastructure.Account;
import com.example.demo.domain.infrastructure.AccountRepository;
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

        Optional<Account> account = accountRepository.findByUserEmail(userEmail);

        if(account.isEmpty()) {
            throw new AccountNotExistException("사용자 없음");
        }else{
            return new CustomUserDetail(account.get());
        }
    }
}
