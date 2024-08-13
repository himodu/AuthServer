package com.example.demo.global.config;

import com.example.demo.domain.oauth.service.CustomSuccessHandler;
import com.example.demo.domain.oauth.service.CustomOauth2UserService;
import com.example.demo.domain.oauth.filter.OauthJwtFilter;
import com.example.demo.domain.oauth.jwt.OAuthJwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final JWTUtile jwtUtile;

    private final OAuthJwtUtil oAuthJwtUtil;
    private final CustomOauth2UserService customOauth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{



        return http.csrf(AbstractHttpConfigurer::disable)
                .formLogin((auth)->auth.disable())
                .httpBasic((auth)->auth.disable())
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource(){
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("localhost:3000"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }))
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOauth2UserService)))
                        .successHandler(customSuccessHandler)
                )
                .authorizeHttpRequests((authorize)-> authorize
                        .requestMatchers("api/v0/login", "api/v0/login").permitAll()
                        .requestMatchers("api/v0/*").hasRole("ADMIN")
                        .requestMatchers("api/v1/*").hasAnyRole("USER","ADMIN")
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new OauthJwtFilter(oAuthJwtUtil), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new JwtFilter(jwtUtile), UsernamePasswordAuthenticationFilter.class)
                .build();


    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
