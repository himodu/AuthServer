package com.example.demo.exception;

public class AccountDuplicationException extends RuntimeException{
    public AccountDuplicationException(String message){
        super(message);
    }
}
