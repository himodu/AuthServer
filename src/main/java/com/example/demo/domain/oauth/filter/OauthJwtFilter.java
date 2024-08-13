package com.example.demo.domain.oauth.filter;

import com.example.demo.domain.oauth.jwt.OAuthJwtUtil;
import com.example.demo.domain.oauth.model.CustomOAuth2User;
import com.example.demo.domain.oauth.model.UserDTO;
import com.example.demo.global.exception.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

public class OauthJwtFilter extends OncePerRequestFilter {
    private final OAuthJwtUtil oAuthJwtUtil;

    public OauthJwtFilter(OAuthJwtUtil oAuthJwtUtil){
        this.oAuthJwtUtil = oAuthJwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//
//            System.out.println(cookie.getName());
//            if (cookie.getName().equals("Authorization")) {
//                authorization = cookie.getValue();
//            }
//        }

        //Authorization 헤더 검증
        if (authorization == null) {
            jwtExceptionHandler(response, "token null");
            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰
        String token = authorization;

        //토큰 소멸 시간 검증
        if (oAuthJwtUtil.isExpired(token)) {
            jwtExceptionHandler(response, "token expired");
            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰에서 username과 role 획득
        String username = oAuthJwtUtil.getUsername(token);
        String name = oAuthJwtUtil.getName(token);
        String role = oAuthJwtUtil.getRole(token);

        //userDTO를 생성하여 값 set
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .name(name)
                .role(role)
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);


    }
    public void jwtExceptionHandler(HttpServletResponse response, String message){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            String json = new ObjectMapper().writeValueAsString(ErrorMessage.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(message)
                    .timeStamp(new Date())
                    .build());
            response.getWriter().write(json);
        }catch(Exception e){
            //json이 잘 만들어지지 못한 경우
        }
    }

}
