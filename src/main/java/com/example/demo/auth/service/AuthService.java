package com.example.demo.auth.service;



import com.example.demo.auth.infrastructure.Account;
import com.example.demo.auth.infrastructure.AccountRepository;
import com.example.demo.auth.model.RegisterDto;
import com.example.demo.auth.infrastructure.Role;
import com.example.demo.auth.exception.AccountDuplicationException;
import com.example.demo.auth.jwt.JwtToken;
import com.example.demo.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtToken signIn(String useremail, String password){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(useremail, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }
    @Transactional
    public int register(RegisterDto registerDto){
        System.out.println(registerDto.getUserEmail());

        if(accountRepository.findByUserEmail(registerDto.getUserEmail()).isPresent()){
            throw new AccountDuplicationException("사용자 이름 중복");
        }
        Account account = Account.builder()
                .userEmail(registerDto.getUserEmail())
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(Role.USER)
                .build();

        accountRepository.save(account);

        return account.getId().intValue();
    }
}
