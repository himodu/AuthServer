package com.example.demo.domain.oauth.service;

import com.example.demo.global.infrastructure.AccountEntity;
import com.example.demo.global.infrastructure.AccountRepository;
import com.example.demo.global.infrastructure.Role;
import com.example.demo.domain.oauth.model.CustomOAuth2User;
import com.example.demo.domain.oauth.model.GoogleResponse;
import com.example.demo.domain.oauth.model.OAuth2Response;
import com.example.demo.domain.oauth.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("authService2")
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        if (registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else{
            return null;
        }
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        Optional<AccountEntity> existAccount = accountRepository.findByUsername(username);
        AccountEntity accountEntity;

        if(existAccount.isEmpty()){
            accountEntity = AccountEntity.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .userEmail(oAuth2Response.getEmail())
                    .role(Role.USER)
                    .build();
            accountRepository.save(accountEntity);

            UserDTO userDTO = UserDTO.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .role("ROLE_USER")
                    .build();

            return new CustomOAuth2User(userDTO);

        }else{
            accountEntity = existAccount.get();
            accountEntity.setUsername(username);
            accountEntity.setUserEmail(oAuth2Response.getEmail());

            accountRepository.save(accountEntity);

            UserDTO userDTO = UserDTO.builder()
                    .username(username)
                    .name(oAuth2Response.getName())
                    .role("ROLE_USER")
                    .build();
            return new CustomOAuth2User(userDTO);
        }
    }
}
