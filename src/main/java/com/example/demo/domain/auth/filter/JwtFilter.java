package com.example.demo.domain.auth.filter;

import com.example.demo.domain.auth.jwt.JWTUtile;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@WebFilter
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{
    private final JWTUtile jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        String accesstoken;
        try{
            accesstoken = resolveToken(request);
            if(accesstoken==null){
                throw new JwtException("토큰 인식 불가");
            }
            if(jwtTokenProvider.validateToken(accesstoken)){
                Authentication authentication = jwtTokenProvider.getAuthentication(accesstoken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println(SecurityContextHolder.getContext().getAuthentication());
            }else{
                throw new JwtException("토큰 유효하지 않음");
            }
        } catch(JwtException e){
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
    private String getRefreshToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Refresh");
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

}
