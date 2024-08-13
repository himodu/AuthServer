package com.example.demo.global.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(String message){super(message);}
}
