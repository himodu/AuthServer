package com.example.demo.global.exception;

public class AccountDuplicationException extends RuntimeException{
    public AccountDuplicationException(String message){
        super(message);
    }
}
