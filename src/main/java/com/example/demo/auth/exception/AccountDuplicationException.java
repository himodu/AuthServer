package com.example.demo.auth.exception;

public class AccountDuplicationException extends RuntimeException{
    public AccountDuplicationException(String message){
        super(message);
    }
}
